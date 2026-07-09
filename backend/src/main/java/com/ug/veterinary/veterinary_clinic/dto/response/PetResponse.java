package com.ug.veterinary.veterinary_clinic.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ug.veterinary.veterinary_clinic.entities.Pet;
import com.ug.veterinary.veterinary_clinic.enums.SexEnum;

public record PetResponse(
        Integer id,
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        SexEnum sex,
        BigDecimal weight,
        boolean neutered,
        String photoUrl,
        Integer ownerId,
        String ownerName
) {
    public static PetResponse from(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getSex(),
                pet.getWeight(),
                pet.isNeutered(),
                pet.getPhotoUrl(),
                pet.getOwner().getId(),
                pet.getOwner().getName()
        );
    }
}
