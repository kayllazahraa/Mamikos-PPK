package com.example.mamikos_api.mapper;

import com.example.mamikos_api.dto.KosanRequest;
import com.example.mamikos_api.dto.KosanResponse;
import com.example.mamikos_api.dto.SimpleKosanDto;
import com.example.mamikos_api.entity.Kosan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KosanMapper {

    private final UserMapper userMapper;

    public KosanResponse toKosanResponse(Kosan kosan) {
        if (kosan == null) {
            return null;
        }
        KosanResponse dto = new KosanResponse();
        dto.setId(kosan.getId());
        dto.setNama(kosan.getNama());
        dto.setAlamat(kosan.getAlamat());
        dto.setHargaPerBulan(kosan.getHargaPerBulan());
        dto.setDeskripsi(kosan.getDeskripsi());
        dto.setTersedia(kosan.getTersedia());
        dto.setPemilik(userMapper.toUserDto(kosan.getPemilik()));
        return dto;
    }

    public Kosan toKosan(KosanRequest request) {
        if (request == null) {
            return null;
        }
        return Kosan.builder()
                .nama(request.getNama())
                .alamat(request.getAlamat())
                .hargaPerBulan(request.getHargaPerBulan())
                .deskripsi(request.getDeskripsi())
                .tersedia(request.getTersedia())
                .build();
    }

    public SimpleKosanDto toSimpleKosanDto(Kosan kosan) {
        if (kosan == null) {
            return null;
        }
        SimpleKosanDto dto = new SimpleKosanDto();
        dto.setId(kosan.getId());
        dto.setNama(kosan.getNama());
        return dto;
    }
}