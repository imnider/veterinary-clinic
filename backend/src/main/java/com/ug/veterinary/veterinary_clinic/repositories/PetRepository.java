package com.ug.veterinary.veterinary_clinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ug.veterinary.veterinary_clinic.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwnerId(Integer ownerId);
}
