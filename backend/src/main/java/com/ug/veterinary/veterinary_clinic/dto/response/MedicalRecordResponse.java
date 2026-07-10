package com.ug.veterinary.veterinary_clinic.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ug.veterinary.veterinary_clinic.entities.MedicalRecord;

public record MedicalRecordResponse(
    Integer id,
    Integer petId,
    String petName,
    Integer appointmentId,
    Integer veterinarianId,
    String veterinarianName,
    LocalDateTime recordDate,
    String diagnosis,
    String notes,
    BigDecimal recordedWeight
) {
     public static MedicalRecordResponse from(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId(),
                medicalRecord.getPet().getId(),
                medicalRecord.getPet().getName(),
                medicalRecord.getAppointment().getId(),
                medicalRecord.getVeterinarian().getId(),
                medicalRecord.getVeterinarian().getName(),
                medicalRecord.getRecordDate(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getNotes(),
                medicalRecord.getRecordedWeight()
        );
    }
}
