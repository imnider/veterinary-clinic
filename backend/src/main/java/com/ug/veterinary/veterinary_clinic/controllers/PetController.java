package com.ug.veterinary.veterinary_clinic.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ug.veterinary.veterinary_clinic.annotations.CanCreatePet;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadAllPets;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadClientPets;
import com.ug.veterinary.veterinary_clinic.annotations.CanReadOwnPets;
import com.ug.veterinary.veterinary_clinic.dto.request.RegisterPetRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.ApiResponse;
import com.ug.veterinary.veterinary_clinic.dto.response.PetResponse;
import com.ug.veterinary.veterinary_clinic.services.PetService;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @CanCreatePet
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PetResponse>> registerPet(
            @Valid @RequestPart("data") RegisterPetRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        PetResponse response = petService.registerPet(request, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @CanReadOwnPets
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<PetResponse>>> getMyPets() {
        return ResponseEntity.ok(ApiResponse.success(petService.getMyPets()));
    }

    @CanReadClientPets
    @GetMapping("/{ownerId}")
    public ResponseEntity<ApiResponse<List<PetResponse>>> getPetsByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(ApiResponse.success(petService.getPetsByOwner(ownerId)));
    }

    @CanReadAllPets
    @GetMapping
    public ResponseEntity<ApiResponse<List<PetResponse>>> getAllPets() {
        return ResponseEntity.ok(ApiResponse.success(petService.getAllPets()));
    }
}