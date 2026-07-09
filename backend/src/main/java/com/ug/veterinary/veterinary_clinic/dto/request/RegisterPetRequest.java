package com.ug.veterinary.veterinary_clinic.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;
import com.ug.veterinary.veterinary_clinic.enums.SexEnum;

public record RegisterPetRequest(
        Integer ownerId,

        @NotBlank(message = ValidationConstants.PET_NAME_REQUIRED)
        @Size(max = ValidationConstants.PET_NAME_MAX, message = ValidationConstants.PET_NAME_MAX_MESSAGE)
        String name,

        @NotBlank(message = ValidationConstants.PET_SPECIES_REQUIRED)
        @Size(max = ValidationConstants.PET_SPECIES_MAX, message = ValidationConstants.PET_SPECIES_MAX_MESSAGE)
        String species,

        @Size(max = ValidationConstants.PET_BREED_MAX, message = ValidationConstants.PET_BREED_MAX_MESSAGE)
        String breed,

        @PastOrPresent(message = ValidationConstants.PET_BIRTH_DATE_INVALID)
        LocalDate birthDate,

        @NotNull(message = ValidationConstants.PET_SEX_REQUIRED)
        SexEnum sex,

        @DecimalMin(value = "0.0", inclusive = false, message = ValidationConstants.PET_WEIGHT_INVALID)
        BigDecimal weight,

        Boolean neutered
) {}