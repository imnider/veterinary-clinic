package com.ug.veterinary.veterinary_clinic.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.dto.request.CreateMedicalRecordRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.MedicalRecordResponse;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Appointment;
import com.ug.veterinary.veterinary_clinic.entities.MedicalRecord;
import com.ug.veterinary.veterinary_clinic.entities.Pet;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;
import com.ug.veterinary.veterinary_clinic.exceptions.InvalidOperationException;
import com.ug.veterinary.veterinary_clinic.exceptions.ResourceNotFoundException;
import com.ug.veterinary.veterinary_clinic.repositories.AppointmentRepository;
import com.ug.veterinary.veterinary_clinic.repositories.MedicalRecordRepository;
import com.ug.veterinary.veterinary_clinic.repositories.PetRepository;
import com.ug.veterinary.veterinary_clinic.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public MedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request) {

        Appointment appointment = appointmentRepository.findById(request.appointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.APPOINTMENT_NOT_FOUND));

        if (medicalRecordRepository.existsByAppointmentId(appointment.getId())) {
            throw new InvalidOperationException(MessageConstants.MEDICAL_RECORD_ALREADY_EXISTS);
        }

        AppUser veterinarian = SecurityUtils.getCurrentUser().getAppUser();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .pet(appointment.getPet())
                .appointment(appointment)
                .veterinarian(veterinarian)
                .recordDate(LocalDateTime.now())
                .diagnosis(request.diagnosis())
                .notes(request.notes())
                .recordedWeight(request.recordedWeight())
                .build();

        return MedicalRecordResponse.from(
                medicalRecordRepository.save(medicalRecord)
        );
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getMedicalRecordsByPet(Integer petId) {
        Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PET_NOT_FOUND));
        validateMedicalRecordAccess(pet);
        return medicalRecordRepository.findByPetIdOrderByRecordDateDesc(petId)
                .stream()
                .map(MedicalRecordResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse getMedicalRecordByAppointment(Integer appointmentId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.MEDICAL_RECORD_NOT_FOUND));
        validateMedicalRecordAccess(medicalRecord.getPet());
        return MedicalRecordResponse.from(medicalRecord);
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getAllMedicalRecords() {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();

        if (currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.ADMIN)) {
            return medicalRecordRepository.findAll()
                    .stream()
                    .map(MedicalRecordResponse::from)
                    .toList();

        } else if (currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.VETERINARIO)) {
            return medicalRecordRepository.findByVeterinarianId(currentUser.getId())
                    .stream()
                    .map(MedicalRecordResponse::from)
                    .toList();

        } else {
            return medicalRecordRepository.findByPetOwnerId(currentUser.getId())
                    .stream()
                    .map(MedicalRecordResponse::from)
                    .toList();
        }
    }

    private void validateMedicalRecordAccess(Pet pet) {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();
        boolean isClient = currentUser.getRoles().stream().anyMatch(role -> role.getName() == RoleEnum.CLIENTE);
        if (!isClient) return;
        
        if (!pet.getOwner().getId().equals(currentUser.getId())) 
            throw new AccessDeniedException(MessageConstants.ACCESS_DENIED);
    }

}
