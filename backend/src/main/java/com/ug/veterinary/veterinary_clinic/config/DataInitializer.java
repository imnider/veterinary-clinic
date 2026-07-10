package com.ug.veterinary.veterinary_clinic.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Role;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;
import com.ug.veterinary.veterinary_clinic.repositories.AppUserRepository;
import com.ug.veterinary.veterinary_clinic.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {
        if (appUserRepository.findByUsername(adminProperties.username()).isPresent()) {
            log.info(MessageConstants.ADMIN_ALREADY_EXISTS);
            return;
        }

        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseThrow(() -> new IllegalStateException(MessageConstants.ADMIN_ROLE_NOT_FOUND));

        AppUser admin = AppUser.builder()
                .name(adminProperties.name())
                .username(adminProperties.username())
                .email(adminProperties.email())
                .passwordHash(passwordEncoder.encode(adminProperties.password()))
                .roles(Set.of(adminRole))
                .build();

        appUserRepository.save(admin);
        log.info(MessageConstants.ADMIN_CREATED);
    }
}
