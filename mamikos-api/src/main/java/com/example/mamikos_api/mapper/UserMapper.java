package com.example.mamikos_api.mapper;

import com.example.mamikos_api.dto.ProfileResponseDto;
import com.example.mamikos_api.dto.UserDto;
import com.example.mamikos_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setNamaLengkap(user.getNamaLengkap());
        return dto;
    }

    public ProfileResponseDto toProfileResponseDto(User user) {
        if (user == null) {
            return null;
        }
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setId(user.getId());
        dto.setNamaLengkap(user.getNamaLengkap());
        dto.setUsername(user.getUsername());
        dto.setNomorTelefon(user.getNomorTelefon());
        dto.setRole(user.getRole().name());
        return dto;
    }
}