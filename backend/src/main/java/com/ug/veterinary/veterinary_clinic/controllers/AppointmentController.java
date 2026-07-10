package com.ug.veterinary.veterinary_clinic.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ug.veterinary.veterinary_clinic.annotations.CanCreateAppointment;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadAppointment;
import com.ug.veterinary.veterinary_clinic.dto.request.CreateAppointmentRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.ApiResponse;
import com.ug.veterinary.veterinary_clinic.dto.response.AppointmentResponse;
import com.ug.veterinary.veterinary_clinic.services.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @CanCreateAppointment
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request
    ) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @CanReadAppointment
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success(appointmentService.getAppointments(false))
        );
    }

    @CanReadAppointment
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getPendingAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success(appointmentService.getAppointments(true))
        );
    }

    @CanReadAppointment
    @GetMapping("/pets/{petId}/pending")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getPendingAppointmentsByPet(@PathVariable Integer petId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        appointmentService.getAppointmentsByPet(petId, true)
                )
        );
    }

    @CanReadAppointment
    @GetMapping("/pets/{petId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPet(@PathVariable Integer petId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        appointmentService.getAppointmentsByPet(petId, false)
                )
        );
    }
}
