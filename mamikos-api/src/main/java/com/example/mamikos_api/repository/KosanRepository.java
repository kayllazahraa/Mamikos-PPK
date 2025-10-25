package com.example.mamikos_api.repository;

import com.example.mamikos_api.entity.Kosan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KosanRepository extends JpaRepository<Kosan, Long> {

    // mencari kosan berdasarkan id pemilik
    List<Kosan> findByPemilikId(Long pemilikId);
}