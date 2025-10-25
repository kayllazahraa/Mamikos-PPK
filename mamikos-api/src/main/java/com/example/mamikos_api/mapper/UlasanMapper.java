package com.example.mamikos_api.mapper;

import com.example.mamikos_api.dto.UlasanResponse;
import com.example.mamikos_api.entity.Ulasan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UlasanMapper {

    private final UserMapper userMapper;

    public UlasanResponse toUlasanResponse(Ulasan ulasan) {
        if (ulasan == null) {
            return null;
        }
        UlasanResponse dto = new UlasanResponse();
        dto.setId(ulasan.getId());
        dto.setRating(ulasan.getRating());
        dto.setKomentar(ulasan.getKomentar());
        dto.setUser(userMapper.toUserDto(ulasan.getUser()));
        return dto;
    }
}