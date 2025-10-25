package com.example.mamikos_api.controller;

import com.example.mamikos_api.dto.UlasanRequest;
import com.example.mamikos_api.dto.UlasanResponse;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.service.UlasanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UlasanController {

    private final UlasanService ulasanService;

    // --- Endpoint Publik ---

    @GetMapping("/kosan/{kosanId}/ulasan")
    public ResponseEntity<List<UlasanResponse>> getUlasanByKosan(
            @PathVariable Long kosanId
    ) {
        return ResponseEntity.ok(ulasanService.getUlasanByKosan(kosanId));
    }

    // --- Endpoint Terproteksi (ROLE_PENCARI) ---

    @PostMapping("/kosan/{kosanId}/ulasan")
    public ResponseEntity<UlasanResponse> createUlasan(
            @PathVariable Long kosanId,
            @Valid @RequestBody UlasanRequest request,
            @AuthenticationPrincipal User user
    ) {
        UlasanResponse response = ulasanService.createUlasan(kosanId, request, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/ulasan/{ulasanId}")
    public ResponseEntity<Void> deleteUlasan(
            @PathVariable Long ulasanId,
            @AuthenticationPrincipal User user
    ) {
        ulasanService.deleteUlasan(ulasanId, user);
        return ResponseEntity.noContent().build();
    }
}