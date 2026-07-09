package com.ug.veterinary.veterinary_clinic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "first-user")
public record FirstUserProperties(String name, String username, String email, String password) {}
