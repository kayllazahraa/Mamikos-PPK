package com.example.mamikos_api.dto;

import lombok.Data;

@Data
public class ProfileResponseDto {
    private Long id;
    private String namaLengkap;
    private String username;
    private String nomorTelefon;
    private String role;
}

