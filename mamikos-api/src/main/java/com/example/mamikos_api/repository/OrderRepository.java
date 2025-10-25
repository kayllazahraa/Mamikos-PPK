package com.example.mamikos_api.repository;

import com.example.mamikos_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // mencari order berdasarkan id pencari kos
    List<Order> findByUserId(Long userId);

    // mencari order untuk kos yang dimiliki oleh pemilik tertentu
    List<Order> findByKosanPemilikId(Long pemilikId);
}