package com.ug.veterinary.veterinary_clinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.dto.request.RegisterUserRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.UserResponse;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Role;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;
import com.ug.veterinary.veterinary_clinic.exceptions.InvalidOperationException;
import com.ug.veterinary.veterinary_clinic.exceptions.ResourceNotFoundException;
import com.ug.veterinary.veterinary_clinic.repositories.AppUserRepository;
import com.ug.veterinary.veterinary_clinic.repositories.RoleRepository;
import com.ug.veterinary.veterinary_clinic.security.SecurityUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(RegisterUserRequest request) {
        if (appUserRepository.existsByUsername(request.username())) {
            throw new InvalidOperationException(MessageConstants.USERNAME_ALREADY_EXISTS);
        }

        if (appUserRepository.existsByEmail(request.email())) {
            throw new InvalidOperationException(MessageConstants.EMAIL_ALREADY_EXISTS);
        }

        Set<Role> roles = resolveRoles(request.roles());

        AppUser user = AppUser.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .phone(request.phone())
                .passwordHash(passwordEncoder.encode(request.password()))
                .specialty(request.specialty())
                .address(request.address())
                .roles(roles)
                .build();

        AppUser savedUser = appUserRepository.save(user);
        return UserResponse.from(savedUser);
    }

    public UserResponse getCurrentUserProfile() {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();
        return UserResponse.from(currentUser);
    }

    private Set<Role> resolveRoles(Set<RoleEnum> roleEnums) {
        Set<Role> roles = new HashSet<>();

        for (RoleEnum roleEnum : roleEnums) {
            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.ROLE_NOT_FOUND));
            roles.add(role);
        }

        return roles;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        AppUser currentUser = SecurityUtils.getCurrentUser().getAppUser();

        if (currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.ADMIN)) {
            return appUserRepository.findAll()
                    .stream()
                    .map(UserResponse::from)
                    .toList();
        } else {
            return appUserRepository.findByRolesName(RoleEnum.VETERINARIO)
                    .stream()
                    .map(UserResponse::from)
                    .toList();
        }
    }
}
