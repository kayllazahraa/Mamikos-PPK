package com.example.mamikos_api.service;

import com.example.mamikos_api.dto.OrderRequest;
import com.example.mamikos_api.dto.OrderResponse;
import com.example.mamikos_api.entity.Kosan;
import com.example.mamikos_api.entity.Order;
import com.example.mamikos_api.entity.OrderStatus;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.mapper.OrderMapper;
import com.example.mamikos_api.repository.KosanRepository;
import com.example.mamikos_api.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KosanRepository kosanRepository;
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest request, User user) {
        Kosan kosan = kosanRepository.findById(request.getKosanId())
                .orElseThrow(() -> new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + request.getKosanId()));

        if (!kosan.getTersedia()) {
            throw new IllegalStateException("Kosan tidak tersedia untuk dipesan");
        }

        Order order = Order.builder()
                .user(user)
                .kosan(kosan)
                .tanggalPesan(LocalDate.now())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public List<OrderResponse> getMyBookings(User user) {
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getMyRentals(User pemilik) {
        return orderRepository.findByKosanPemilikId(pemilik.getId())
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long orderId, String status, User pemilik) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order tidak ditemukan dengan ID: " + orderId));

        if (!order.getKosan().getPemilik().getId().equals(pemilik.getId())) {
            throw new SecurityException("Anda bukan pemilik kosan dari order ini");
        }

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status tidak valid. Gunakan 'PENDING', 'APPROVED', 'REJECTED', 'CANCELLED'.");
        }

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(updatedOrder);
    }

    public OrderResponse getOrderById(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order tidak ditemukan dengan ID: " + orderId));

        boolean isPembuatOrder = order.getUser().getId().equals(user.getId());
        boolean isPemilikKosan = order.getKosan().getPemilik().getId().equals(user.getId());

        if (!isPembuatOrder && !isPemilikKosan) {
            throw new SecurityException("Anda tidak memiliki akses ke order ini");
        }

        return orderMapper.toOrderResponse(order);
    }
}