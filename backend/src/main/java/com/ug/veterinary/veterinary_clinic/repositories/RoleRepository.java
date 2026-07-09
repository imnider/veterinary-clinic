package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.Role;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
