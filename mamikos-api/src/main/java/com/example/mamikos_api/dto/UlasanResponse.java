package com.example.mamikos_api.dto;

import lombok.Data;

@Data
public class UlasanResponse {
    private Long id;
    private Integer rating;
    private String komentar;
    private UserDto user;
}