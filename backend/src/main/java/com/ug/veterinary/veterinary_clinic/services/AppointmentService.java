package com.ug.veterinary.veterinary_clinic.services;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.dto.request.CreateAppointmentRequest;
import com.ug.veterinary.veterinary_clinic.dto.request.UpdateAppointmentStatusRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.AppointmentResponse;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Appointment;
import com.ug.veterinary.veterinary_clinic.entities.Pet;
import com.ug.veterinary.veterinary_clinic.enums.AppointmentStatusEnum;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;
import com.ug.veterinary.veterinary_clinic.exceptions.InvalidOperationException;
import com.ug.veterinary.veterinary_clinic.exceptions.ResourceNotFoundException;
import com.ug.veterinary.veterinary_clinic.repositories.AppUserRepository;
import com.ug.veterinary.veterinary_clinic.repositories.AppointmentRepository;
import com.ug.veterinary.veterinary_clinic.repositories.PetRepository;
import com.ug.veterinary.veterinary_clinic.security.CustomUserDetails;
import com.ug.veterinary.veterinary_clinic.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        Pet pet = resolvePet(request.petId());
        AppUser veterinarian = resolveVeterinarian(request.veterinarianId());

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .veterinarian(veterinarian)
                .appointmentDate(request.appointmentDate())
                .reason(request.reason())
                .appointmentType(request.appointmentType())
                .status(AppointmentStatusEnum.SCHEDULED)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointments(boolean pendingOnly) {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();
        List<Appointment> appointments;

        if (currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.ADMIN)) {
            appointments = pendingOnly
                    ? appointmentRepository.findByStatus(AppointmentStatusEnum.SCHEDULED)
                    : appointmentRepository.findAll();

        } else if (currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.VETERINARIO)) {
            appointments = pendingOnly
                    ? appointmentRepository.findByVeterinarianIdAndStatus(
                            currentUser.getId(),
                            AppointmentStatusEnum.SCHEDULED)
                    : appointmentRepository.findByVeterinarianId(currentUser.getId());
        } else {
            appointments = pendingOnly
                    ? appointmentRepository.findByPetOwnerIdAndStatus(
                            currentUser.getId(),
                            AppointmentStatusEnum.SCHEDULED)
                    : appointmentRepository.findByPetOwnerId(currentUser.getId());
        }

        return appointments.stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPet(Integer petId, boolean pendingOnly) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PET_NOT_FOUND));
        validatePetAccess(pet);
        List<Appointment> appointments = pendingOnly
                ? appointmentRepository.findByPetIdAndStatus(
                        petId,
                        AppointmentStatusEnum.SCHEDULED)
                : appointmentRepository.findByPetId(petId);
        return appointments.stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Integer appointmentId,UpdateAppointmentStatusRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
        .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.APPOINTMENT_NOT_FOUND));
                validateAppointmentStatusUpdate(appointment, request.status());
        appointment.setStatus(request.status());
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    private Pet resolvePet(Integer petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PET_NOT_FOUND));

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();
        boolean isClient = currentUser.getAppUser().getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.CLIENTE);

        if (isClient && !pet.getOwner().getId().equals(currentUser.getId())) {
            throw new InvalidOperationException(MessageConstants.APPOINTMENT_PET_NOT_OWNED);
        }

        return pet;
    }

    private AppUser resolveVeterinarian(Integer veterinarianId) {
        AppUser veterinarian = appUserRepository.findById(veterinarianId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.VETERINARIAN_NOT_FOUND));
        boolean isVeterinarian = veterinarian.getRoles().stream().anyMatch(role -> role.getName() == RoleEnum.VETERINARIO);
        if (!isVeterinarian) throw new InvalidOperationException(MessageConstants.VETERINARIAN_INVALID_ROLE);
        return veterinarian;
    }

    private void validatePetAccess(Pet pet) {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();
        boolean isClient = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.CLIENTE);
        
        if (!isClient) return;

        if (!pet.getOwner().getId().equals(currentUser.getId()))
            throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);   
    }

    private void validateAppointmentStatusUpdate(Appointment appointment, AppointmentStatusEnum newStatus) {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.ADMIN);
        boolean isVeterinarian = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.VETERINARIO);
        boolean isClient = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.CLIENTE);

        if (appointment.getStatus() != AppointmentStatusEnum.SCHEDULED) {
                throw new InvalidOperationException(MessageConstants.APPOINTMENT_STATUS_CANNOT_BE_UPDATED);
        }

        if (isClient) {
                validatePetAccess(appointment.getPet());
                if (newStatus != AppointmentStatusEnum.CANCELLED)
                        throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);
                return;
        }

        if (isVeterinarian) {
                if (!appointment.getVeterinarian().getId().equals(currentUser.getId()))
                        throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);
        
                if (newStatus != AppointmentStatusEnum.COMPLETED) 
                        throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);
                return;
        }

        if (isAdmin) return;
        
        throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);
    }
}
