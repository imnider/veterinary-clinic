package com.ug.veterinary.veterinary_clinic.security;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
