package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<AppUser> findByRolesName(RoleEnum roleName);

}