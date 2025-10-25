package com.example.mamikos_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ulasan")
public class Ulasan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String komentar;

    //Satu Ulasan diberikan oleh satu User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Satu Ulasan untuk satu Kosan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kosan_id", nullable = false)
    private Kosan kosan;
}