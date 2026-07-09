package com.ug.veterinary.veterinary_clinic.dto.request;


import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = ValidationConstants.USERNAME_REQUIRED)
        String username,

        @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED)
        String password
) {}
