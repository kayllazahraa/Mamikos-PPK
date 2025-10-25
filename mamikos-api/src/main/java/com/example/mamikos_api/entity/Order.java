package com.example.mamikos_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate tanggalPesan;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Relasi: Satu Order dibuat oleh satu User (Pencari)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relasi: Satu Order untuk satu Kosan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kosan_id", nullable = false)
    private Kosan kosan;
}