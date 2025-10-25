package com.example.mamikos_api.controller;

import com.example.mamikos_api.dto.OrderRequest;
import com.example.mamikos_api.dto.OrderResponse;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // --- Endpoint Terproteksi (ROLE_PENCARI) ---

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request,
            @AuthenticationPrincipal User user
    ) {
        OrderResponse response = orderService.createOrder(request, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<OrderResponse>> getMyBookings(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.getMyBookings(user));
    }

    // --- Endpoint Terproteksi (ROLE_PEMILIK) ---

    @GetMapping("/my-rentals")
    public ResponseEntity<List<OrderResponse>> getMyRentals(
            @AuthenticationPrincipal User pemilik
    ) {
        return ResponseEntity.ok(orderService.getMyRentals(pemilik));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate, // Menerima JSON: { "status": "APPROVED" }
            @AuthenticationPrincipal User pemilik
    ) {
        String status = statusUpdate.get("status");
        if (status == null) {
            throw new IllegalArgumentException("Key 'status' tidak ditemukan di JSON body");
        }
        OrderResponse response = orderService.updateOrderStatus(id, status, pemilik);
        return ResponseEntity.ok(response);
    }

    // --- Endpoint Terproteksi (ROLE_PENCARI atau ROLE_PEMILIK) ---

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.getOrderById(id, user));
    }
}