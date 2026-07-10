package com.ug.veterinary.veterinary_clinic.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

import com.ug.veterinary.veterinary_clinic.constants.ValidationConstants;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;

public record RegisterUserRequest(
        @NotBlank(message = ValidationConstants.USER_NAME_REQUIRED)
        @Size(max = ValidationConstants.USER_NAME_MAX, message = ValidationConstants.USER_NAME_MAX_MESSAGE)
        String name,

        @NotBlank(message = ValidationConstants.USERNAME_REQUIRED)
        @Size(max = ValidationConstants.USERNAME_MAX, message = ValidationConstants.USERNAME_MAX_MESSAGE)
        String username,

        @NotBlank(message = ValidationConstants.EMAIL_REQUIRED)
        @Email(message = ValidationConstants.EMAIL_FORMAT)
        @Size(max = ValidationConstants.EMAIL_MAX, message = ValidationConstants.EMAIL_MAX_MESSAGE)
        String email,

        @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED)
        @Size(min = ValidationConstants.PASSWORD_MIN, message = ValidationConstants.PASSWORD_MIN_MESSAGE)
        String password,

        String phone,

        String specialty,

        String address,

        @NotEmpty(message = ValidationConstants.ROLES_REQUIRED)
        Set<RoleEnum> roles
) {}
