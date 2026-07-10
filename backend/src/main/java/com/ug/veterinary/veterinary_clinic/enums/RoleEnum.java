package com.ug.veterinary.veterinary_clinic.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoleEnum {

    CLIENTE(Set.of(
            Permission.PET_CREATE,
            Permission.PET_READ_OWN,
            Permission.APPOINTMENT_CREATE,
            Permission.APPOINTMENT_READ,
            Permission.MEDICAL_RECORD_READ,
            Permission.VACCINATION_READ
    )),

    VETERINARIO(Set.of(
            Permission.PET_READ,
            Permission.APPOINTMENT_READ_ALL,
            Permission.APPOINTMENT_UPDATE,
            Permission.MEDICAL_RECORD_CREATE,
            Permission.MEDICAL_RECORD_READ,
            Permission.MEDICAL_RECORD_UPDATE,
            Permission.VACCINATION_CREATE,
            Permission.VACCINATION_READ,
            Permission.VACCINATION_UPDATE
    )),

    ADMIN(Set.of(Permission.values()));

    private final Set<Permission> permissions;

    RoleEnum(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toCollection(HashSet::new));

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

    public enum Permission {
        // Pet
        PET_CREATE,
        PET_READ_OWN,
        PET_READ,
        PET_READ_ALL,
        PET_UPDATE,
        PET_DELETE,

        // Appointment
        APPOINTMENT_CREATE,
        APPOINTMENT_READ,
        APPOINTMENT_READ_ALL,
        APPOINTMENT_UPDATE,
        APPOINTMENT_DELETE,

        // Medical record
        MEDICAL_RECORD_CREATE,
        MEDICAL_RECORD_READ,
        MEDICAL_RECORD_READ_ALL,
        MEDICAL_RECORD_UPDATE,
        MEDICAL_RECORD_DELETE,

        // Vaccination record
        VACCINATION_CREATE,
        VACCINATION_READ,
        VACCINATION_UPDATE,
        VACCINATION_DELETE,

        // Service (catalog)
        SERVICE_CREATE,
        SERVICE_READ,
        SERVICE_UPDATE,
        SERVICE_DELETE,

        // Treatment (catalog)
        TREATMENT_CREATE,
        TREATMENT_READ,
        TREATMENT_UPDATE,
        TREATMENT_DELETE,

        // Vaccine (catalog)
        VACCINE_CREATE,
        VACCINE_READ,
        VACCINE_UPDATE,
        VACCINE_DELETE,

        // User
        USER_CREATE,
        USER_READ,
        USER_UPDATE,
        USER_DELETE
    }
}