package com.ug.veterinary.veterinary_clinic.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoleEnum {

    CLIENTE(Set.of(
            Permission.PET_READ,
            Permission.APPOINTMENT_READ,
            Permission.APPOINTMENT_WRITE,
            Permission.MEDICAL_RECORD_READ,
            Permission.VACCINATION_READ
    )),

    VETERINARIO(Set.of(
            Permission.PET_READ,
            Permission.APPOINTMENT_READ_ALL,
            Permission.APPOINTMENT_WRITE,
            Permission.MEDICAL_RECORD_READ,
            Permission.MEDICAL_RECORD_WRITE,
            Permission.VACCINATION_READ,
            Permission.VACCINATION_WRITE
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
        PET_READ,
        PET_WRITE,
        APPOINTMENT_READ,
        APPOINTMENT_READ_ALL,
        APPOINTMENT_WRITE,
        MEDICAL_RECORD_READ,
        MEDICAL_RECORD_WRITE,
        VACCINATION_READ,
        VACCINATION_WRITE,
        CATALOG_MANAGE,
        USER_MANAGE
    }
}