package com.example.mamikos_api.repository;

import com.example.mamikos_api.entity.Ulasan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UlasanRepository extends JpaRepository<Ulasan, Long> {

    // mencari ulasan berdasarkan ID kosan
    List<Ulasan> findByKosanId(Long kosanId);
}