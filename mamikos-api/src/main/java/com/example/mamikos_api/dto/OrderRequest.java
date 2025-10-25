package com.example.mamikos_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull(message = "ID Kosan tidak boleh kosong")
    private Long kosanId;
}