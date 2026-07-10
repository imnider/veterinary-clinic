package com.ug.veterinary.veterinary_clinic.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ug.veterinary.veterinary_clinic.annotations.CanCreateUser;
import com.ug.veterinary.veterinary_clinic.dto.request.RegisterUserRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.ApiResponse;
import com.ug.veterinary.veterinary_clinic.dto.response.UserResponse;
import com.ug.veterinary.veterinary_clinic.services.AppUserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService appUserService;

    @CanCreateUser
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = appUserService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }
}
