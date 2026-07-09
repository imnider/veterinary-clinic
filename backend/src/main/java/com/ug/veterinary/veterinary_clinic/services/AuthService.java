package com.ug.veterinary.veterinary_clinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ug.veterinary.veterinary_clinic.dto.request.LoginRequest;
import com.ug.veterinary.veterinary_clinic.dto.response.AuthResponse;
import com.ug.veterinary.veterinary_clinic.jwt.JwtService;
import com.ug.veterinary.veterinary_clinic.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}