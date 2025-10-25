package com.example.mamikos_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kosan")
public class Kosan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private Integer hargaPerBulan;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    private Boolean tersedia;

    //Satu Kosan dimiliki oleh satu pemilik
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pemilik_id", nullable = false) // Foreign Key ke tabel users
    private User pemilik;

    //Satu Kosan bisa memiliki banyak ulasan
    @OneToMany(mappedBy = "kosan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ulasan> ulasan;

    //Satu kos bisa memiliki banyak order
    @OneToMany(mappedBy = "kosan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}