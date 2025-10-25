package com.example.mamikos_api.controller;

import com.example.mamikos_api.dto.KosanRequest;
import com.example.mamikos_api.dto.KosanResponse;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.service.KosanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kosan")
@RequiredArgsConstructor
public class KosanController {

    private final KosanService kosanService;

    // --- Endpoint Publik ---

    @GetMapping
    public ResponseEntity<List<KosanResponse>> getAllKosan() {
        return ResponseEntity.ok(kosanService.getAllKosan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KosanResponse> getKosanById(@PathVariable Long id) {
        return ResponseEntity.ok(kosanService.getKosanById(id));
    }

    // --- Endpoint Terproteksi (ROLE_PEMILIK) ---

    @PostMapping
    public ResponseEntity<KosanResponse> createKosan(
            @Valid @RequestBody KosanRequest request,
            @AuthenticationPrincipal User pemilik
    ) {
        KosanResponse response = kosanService.createKosan(request, pemilik);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KosanResponse> updateKosan(
            @PathVariable Long id,
            @Valid @RequestBody KosanRequest request,
            @AuthenticationPrincipal User pemilik
    ) {
        KosanResponse response = kosanService.updateKosan(id, request, pemilik);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKosan(
            @PathVariable Long id,
            @AuthenticationPrincipal User pemilik
    ) {
        kosanService.deleteKosan(id, pemilik);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-listings")
    public ResponseEntity<List<KosanResponse>> getMyListings(
            @AuthenticationPrincipal User pemilik
    ) {
        return ResponseEntity.ok(kosanService.getMyListings(pemilik));
    }
}