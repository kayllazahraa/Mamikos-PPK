package com.example.mamikos_api.mapper;

import com.example.mamikos_api.dto.OrderResponse;
import com.example.mamikos_api.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final KosanMapper kosanMapper;

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }
        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setTanggalPesan(order.getTanggalPesan());
        dto.setStatus(order.getStatus().name());
        dto.setUser(userMapper.toUserDto(order.getUser()));
        dto.setKosan(kosanMapper.toSimpleKosanDto(order.getKosan()));
        return dto;
    }
}