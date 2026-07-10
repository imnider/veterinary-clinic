package com.ug.veterinary.veterinary_clinic.dto.request;

import java.math.BigDecimal;

import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateMedicalRecordRequest(

    @NotNull(message = ValidationConstants.MEDICAL_RECORD_APPOINTMENT_REQUIRED)
    Integer appointmentId,

    @NotBlank(message = ValidationConstants.MEDICAL_RECORD_DIAGNOSIS_REQUIRED)
    @Size(max = ValidationConstants.MEDICAL_RECORD_DIAGNOSIS_MAX, message = ValidationConstants.MEDICAL_RECORD_DIAGNOSIS_MAX_MESSAGE)
    String diagnosis,

    @Size(max = ValidationConstants.MEDICAL_RECORD_NOTES_MAX, message = ValidationConstants.MEDICAL_RECORD_NOTES_MAX_MESSAGE)
    String notes,

    @Positive(message = ValidationConstants.MEDICAL_RECORD_WEIGHT_INVALID)
    BigDecimal recordedWeight
    
) {}