package com.ug.veterinary.veterinary_clinic.services;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ug.veterinary.veterinary_clinic.cloudinary.CloudinaryService;
import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.dto.request.RegisterPetRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.PetResponse;
import com.ug.veterinary.veterinary_clinic.entities.AppUser;
import com.ug.veterinary.veterinary_clinic.entities.Pet;
import com.ug.veterinary.veterinary_clinic.enums.RoleEnum;
import com.ug.veterinary.veterinary_clinic.exceptions.InvalidOperationException;
import com.ug.veterinary.veterinary_clinic.exceptions.ResourceNotFoundException;
import com.ug.veterinary.veterinary_clinic.repositories.AppUserRepository;
import com.ug.veterinary.veterinary_clinic.repositories.PetRepository;
import com.ug.veterinary.veterinary_clinic.security.CustomUserDetails;
import com.ug.veterinary.veterinary_clinic.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class PetService {

    private static final String CLOUDINARY_PET_FOLDER = "veterinary-clinic/pets";

    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public PetResponse registerPet(RegisterPetRequest request, MultipartFile photo) {
        AppUser owner = resolveOwner(request.ownerId());

        Pet pet = Pet.builder()
                .owner(owner)
                .name(request.name())
                .species(request.species())
                .breed(request.breed())
                .birthDate(request.birthDate())
                .sex(request.sex())
                .weight(request.weight())
                .neutered(Boolean.TRUE.equals(request.neutered()))
                .build();

        CloudinaryService.ImageUploadResult uploadResult = null;

        if (photo != null && !photo.isEmpty()) {
            uploadResult = cloudinaryService.upload(photo, CLOUDINARY_PET_FOLDER);
            pet.updatePhoto(uploadResult.url(), uploadResult.publicId());
        }

        try {
            Pet savedPet = petRepository.save(pet);
            return PetResponse.from(savedPet);
        } catch (RuntimeException ex) {
            if (uploadResult != null) {
                cloudinaryService.delete(uploadResult.publicId());
            }
            throw ex;
        }
    }

    public List<PetResponse> getMyPets() {
        Integer ownerId = SecurityUtils.getCurrentUser().getId();

        return petRepository.findByOwnerId(ownerId)
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    public List<PetResponse> getPetsByOwner(Integer ownerId) {
        return petRepository.findByOwnerId(ownerId)
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    public List<PetResponse> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    private AppUser resolveOwner(Integer ownerId) {
        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        if (ownerId == null) {
            boolean isClient = currentUser.getAppUser().getRoles().stream()
                    .anyMatch(role -> role.getName() == RoleEnum.CLIENTE);

            if (!isClient) {
                throw new InvalidOperationException(MessageConstants.PET_OWNER_ID_REQUIRED);
            }

            return currentUser.getAppUser();
        }

        AppUser owner = appUserRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PET_OWNER_NOT_FOUND));

        boolean isClient = owner.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleEnum.CLIENTE);

        if (!isClient) {
            throw new InvalidOperationException(MessageConstants.PET_OWNER_INVALID_ROLE);
        }

        return owner;
    }
}