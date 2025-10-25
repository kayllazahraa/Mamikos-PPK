package com.example.mamikos_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KosanRequest {

    @NotBlank(message = "Nama kosan tidak boleh kosong")
    private String nama;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;

    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1, message = "Harga harus positif")
    private Integer hargaPerBulan;

    private String deskripsi;

    @NotNull(message = "Status ketersediaan tidak boleh kosong")
    private Boolean tersedia;
}