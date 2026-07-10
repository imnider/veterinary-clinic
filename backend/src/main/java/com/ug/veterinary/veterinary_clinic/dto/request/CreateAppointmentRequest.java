package com.ug.veterinary.veterinary_clinic.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;
import com.ug.veterinary.veterinary_clinic.constants.AppointmentConstants;

public record CreateAppointmentRequest(
        @NotNull(message = ValidationConstants.APPOINTMENT_PET_ID_REQUIRED)
        Integer petId,

        @NotNull(message = ValidationConstants.APPOINTMENT_VETERINARIAN_ID_REQUIRED)
        Integer veterinarianId,

        @NotNull(message = ValidationConstants.APPOINTMENT_DATE_REQUIRED)
        @Future(message = ValidationConstants.APPOINTMENT_DATE_FUTURE)
        LocalDateTime appointmentDate,

        @Size(max = ValidationConstants.APPOINTMENT_REASON_MAX, message = ValidationConstants.APPOINTMENT_REASON_MAX_MESSAGE)
        String reason,

        @NotBlank(message = ValidationConstants.APPOINTMENT_TYPE_REQUIRED)
        @Pattern(regexp = AppointmentConstants.TYPE_MEDICAL + "|" + AppointmentConstants.TYPE_SERVICE, message = ValidationConstants.APPOINTMENT_TYPE_INVALID)
        String appointmentType
) {}
