package com.ug.veterinary.veterinary_clinic.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    private final FirstUserProperties firstUserProperties;

    @Override
    public void run(String... args) {
        if (appUserRepository.findByUsername(firstUserProperties.username()).isPresent()) {
            log.info("El usuario administrador ya existe, se omite la creacion.");
            return;
        }

        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseThrow(() -> new IllegalStateException(
                        "El rol ADMIN no existe en la base de datos. Verifica los inserts del script SQL."
                ));

        AppUser admin = AppUser.builder()
                .name(firstUserProperties.name())
                .username(firstUserProperties.username())
                .email(firstUserProperties.email())
                .passwordHash(passwordEncoder.encode(firstUserProperties.password()))
                .roles(Set.of(adminRole))
                .build();

        appUserRepository.save(admin);
        log.info("Usuario administrador creado: {}", firstUserProperties.username());
    }
}
