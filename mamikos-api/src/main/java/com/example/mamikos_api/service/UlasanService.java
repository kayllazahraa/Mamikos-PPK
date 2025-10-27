package com.example.mamikos_api.service;

import com.example.mamikos_api.dto.UlasanRequest;
import com.example.mamikos_api.dto.UlasanResponse;
import com.example.mamikos_api.entity.Kosan;
import com.example.mamikos_api.entity.Ulasan;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.entity.OrderStatus;
import com.example.mamikos_api.mapper.UlasanMapper;
import com.example.mamikos_api.repository.KosanRepository;
import com.example.mamikos_api.repository.OrderRepository;
import com.example.mamikos_api.repository.UlasanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UlasanService {

    private final UlasanRepository ulasanRepository;
    private final KosanRepository kosanRepository;
    private final UlasanMapper ulasanMapper;
    private final OrderRepository orderRepository;

    public List<UlasanResponse> getUlasanByKosan(Long kosanId) {
        if (!kosanRepository.existsById(kosanId)) {
            throw new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + kosanId);
        }

        return ulasanRepository.findByKosanId(kosanId)
                .stream()
                .map(ulasanMapper::toUlasanResponse)
                .collect(Collectors.toList());
    }

    public UlasanResponse createUlasan(Long kosanId, UlasanRequest request, User user) {
        Kosan kosan = kosanRepository.findById(kosanId)
                .orElseThrow(() -> new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + kosanId));

        //periksa apakah user pernah memesan kos dengan status APPROVED
        boolean hasApprovedOrder = orderRepository.existsByUserIdAndKosanIdAndStatus(
                user.getId(),
                kosanId,
                OrderStatus.APPROVED
        );

        if (!hasApprovedOrder) {
            throw new SecurityException("Anda hanya dapat memberi ulasan pada kosan yang pesanannya telah disetujui.");
        }

        Ulasan ulasan = Ulasan.builder()
                .rating(request.getRating())
                .komentar(request.getKomentar())
                .kosan(kosan)
                .user(user)
                .build();

        Ulasan savedUlasan = ulasanRepository.save(ulasan);
        return ulasanMapper.toUlasanResponse(savedUlasan);
    }

    public void deleteUlasan(Long ulasanId, User user) {
        Ulasan ulasan = ulasanRepository.findById(ulasanId)
                .orElseThrow(() -> new EntityNotFoundException("Ulasan tidak ditemukan dengan ID: " + ulasanId));

        if (!ulasan.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Anda bukan pemilik ulasan ini");
        }

        ulasanRepository.delete(ulasan);
    }
}