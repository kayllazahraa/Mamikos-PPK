package com.example.mamikos_api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderResponse {
    private Long id;
    private LocalDate tanggalPesan;
    private String status;
    private UserDto user;
    private SimpleKosanDto kosan;
}

