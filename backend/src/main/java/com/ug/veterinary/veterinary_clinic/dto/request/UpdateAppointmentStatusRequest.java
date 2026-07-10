package com.ug.veterinary.veterinary_clinic.dto.request;

import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;
import com.ug.veterinary.veterinary_clinic.enums.AppointmentStatusEnum;

import jakarta.validation.constraints.NotNull;

public record UpdateAppointmentStatusRequest(

        @NotNull(message = ValidationConstants.APPOINTMENT_STATUS_REQUIRED)
        AppointmentStatusEnum status

) {}
