package com.example.mamikos_api.dto;

import lombok.Data;

@Data
public class KosanResponse {
    private Long id;
    private String nama;
    private String alamat;
    private Integer hargaPerBulan;
    private String deskripsi;
    private Boolean tersedia;
    private UserDto pemilik;
}