package com.example.mamikos_api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    private String namaLengkap;
    private String username;
    private String nomorTelefon;

    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

}