package com.example.mamikos_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UlasanRequest {

    @NotNull(message = "Rating tidak boleh kosong")
    @Min(value = 1, message = "Rating minimal 1")
    @Max(value = 5, message = "Rating maximal 5")
    private Integer rating;

    @NotBlank(message = "Komentar tidak boleh kosong")
    private String komentar;
}