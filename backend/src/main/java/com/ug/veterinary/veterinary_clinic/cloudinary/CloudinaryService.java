package com.ug.veterinary.veterinary_clinic.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ug.veterinary.veterinary_clinic.constants.MessageConstants;
import com.ug.veterinary.veterinary_clinic.exceptions.ImageUploadException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public ImageUploadResult upload(MultipartFile file, String folder) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folder,
                    "public_id", UUID.randomUUID().toString(),
                    "resource_type", "image"
            ));

            return new ImageUploadResult(
                    (String) result.get("secure_url"),
                    (String) result.get("public_id")
            );
        } catch (IOException ex) {
            throw new ImageUploadException(MessageConstants.IMAGE_UPLOAD_ERROR);
        }
    }

    public void delete(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return;
        }
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException ex) {
            throw new ImageUploadException(MessageConstants.IMAGE_DELETE_ERROR);
        }
    }

    public record ImageUploadResult(String url, String publicId) {}
}
