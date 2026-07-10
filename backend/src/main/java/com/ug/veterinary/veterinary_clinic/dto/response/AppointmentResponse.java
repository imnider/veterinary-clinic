package com.ug.veterinary.veterinary_clinic.dto.response;

import java.time.LocalDateTime;

import com.ug.veterinary.veterinary_clinic.entities.Appointment;
import com.ug.veterinary.veterinary_clinic.enums.AppointmentStatusEnum;
import com.ug.veterinary.veterinary_clinic.enums.AppointmentTypeEnum;

public record AppointmentResponse(
        Integer id,
        Integer petId,
        String petName,
        Integer veterinarianId,
        String veterinarianName,
        LocalDateTime appointmentDate,
        String reason,
        AppointmentTypeEnum appointmentType,
        AppointmentStatusEnum status
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPet().getId(),
                appointment.getPet().getName(),
                appointment.getVeterinarian().getId(),
                appointment.getVeterinarian().getName(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getAppointmentType(),
                appointment.getStatus()
        );
    }
}
