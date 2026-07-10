package com.ug.veterinary.veterinary_clinic.dto.response;

import java.util.Set;
import java.util.stream.Collectors;

import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Role;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;

public record UserResponse(
        Integer id,
        String name,
        String username,
        String email,
        String phone,
        String specialty,
        String address,
        Set<RoleEnum> roles
) {
    public static UserResponse from(AppUser user) {
        Set<RoleEnum> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getSpecialty(),
                user.getAddress(),
                roles
        );
    }
}
