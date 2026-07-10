package com.ug.veterinary.veterinary_clinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ug.veterinary.veterinary_clinic.constants.AppointmentConstants;
import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.dto.request.CreateAppointmentRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.AppointmentResponse;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Appointment;
import com.ug.veterinary.veterinary_clinic.entities.Pet;
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
                .status(AppointmentConstants.STATUS_SCHEDULED)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
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

        boolean isVeterinarian = veterinarian.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.VETERINARIO);

        if (!isVeterinarian) {
            throw new InvalidOperationException(MessageConstants.VETERINARIAN_INVALID_ROLE);
        }

        return veterinarian;
    }
}
