package com.example.mamikos_api.controller;

import com.example.mamikos_api.dto.LoginRequest;
import com.example.mamikos_api.dto.LoginResponse;
import com.example.mamikos_api.dto.RegisterRequest;
import com.example.mamikos_api.dto.UpdateProfileRequest;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        User updatedUser = authService.updateProfile(currentUser, request);
        return ResponseEntity.ok(updatedUser);
    }
}