package com.ug.veterinary.veterinary_clinic.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ug.veterinary.veterinary_clinic.annotations.CanCreateMedicalRecord;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadAllMedicalRecords;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadMedicalRecord;
import com.ug.veterinary.veterinary_clinic.dto.request.CreateMedicalRecordRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.ApiResponse;
import com.ug.veterinary.veterinary_clinic.dto.response.MedicalRecordResponse;
import com.ug.veterinary.veterinary_clinic.services.MedicalRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @CanCreateMedicalRecord
    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> createMedicalRecord(@Valid @RequestBody CreateMedicalRecordRequest request) {
        MedicalRecordResponse response = medicalRecordService.createMedicalRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @CanReadAllMedicalRecords
    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getAllMedicalRecords() {
        return ResponseEntity.ok(
                ApiResponse.success(medicalRecordService.getAllMedicalRecords())
        );
    }

    @CanReadMedicalRecord
    @GetMapping("/pets/{petId}")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getMedicalRecordsByPet(@PathVariable Integer petId) {
        return ResponseEntity.ok(
                ApiResponse.success(medicalRecordService.getMedicalRecordsByPet(petId))
        );
    }

    @CanReadMedicalRecord
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getMedicalRecordByAppointment(@PathVariable Integer appointmentId) {
        return ResponseEntity.ok(
                ApiResponse.success(medicalRecordService.getMedicalRecordByAppointment(appointmentId))
        );
    }

}