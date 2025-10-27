package com.example.mamikos_api.controller;

import com.example.mamikos_api.dto.LoginRequest;
import com.example.mamikos_api.dto.LoginResponse;
import com.example.mamikos_api.dto.ProfileResponseDto; // Import DTO baru
import com.example.mamikos_api.dto.RegisterRequest;
import com.example.mamikos_api.dto.UpdateProfileRequest;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.mapper.UserMapper; // Import UserMapper
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
    private final UserMapper userMapper;

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
    public ResponseEntity<ProfileResponseDto> getMyProfile(
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(userMapper.toProfileResponseDto(currentUser));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponseDto> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        ProfileResponseDto updatedUserDto = authService.updateProfile(currentUser, request);
        return ResponseEntity.ok(updatedUserDto);
    }
}