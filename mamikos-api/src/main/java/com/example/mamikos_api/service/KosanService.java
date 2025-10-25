package com.example.mamikos_api.service;

import com.example.mamikos_api.dto.KosanRequest;
import com.example.mamikos_api.dto.KosanResponse;
import com.example.mamikos_api.entity.Kosan;
import com.example.mamikos_api.entity.User;
import com.example.mamikos_api.mapper.KosanMapper;
import com.example.mamikos_api.repository.KosanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KosanService {

    private final KosanRepository kosanRepository;
    private final KosanMapper kosanMapper;

    public List<KosanResponse> getAllKosan() {
        return kosanRepository.findAll()
                .stream()
                .map(kosanMapper::toKosanResponse)
                .collect(Collectors.toList());
    }

    public KosanResponse getKosanById(Long id) {
        Kosan kosan = kosanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + id));
        return kosanMapper.toKosanResponse(kosan);
    }

    public KosanResponse createKosan(KosanRequest request, User pemilik) {
        Kosan kosan = kosanMapper.toKosan(request);
        kosan.setPemilik(pemilik);
        Kosan savedKosan = kosanRepository.save(kosan);
        return kosanMapper.toKosanResponse(savedKosan);
    }

    public KosanResponse updateKosan(Long id, KosanRequest request, User pemilik) {
        Kosan kosan = kosanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + id));

        if (!kosan.getPemilik().getId().equals(pemilik.getId())) {
            throw new SecurityException("Anda bukan pemilik kosan ini");
        }

        kosan.setNama(request.getNama());
        kosan.setAlamat(request.getAlamat());
        kosan.setHargaPerBulan(request.getHargaPerBulan());
        kosan.setDeskripsi(request.getDeskripsi());
        kosan.setTersedia(request.getTersedia());

        Kosan updatedKosan = kosanRepository.save(kosan);
        return kosanMapper.toKosanResponse(updatedKosan);
    }

    public void deleteKosan(Long id, User pemilik) {
        Kosan kosan = kosanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kosan tidak ditemukan dengan ID: " + id));

        if (!kosan.getPemilik().getId().equals(pemilik.getId())) {
            throw new SecurityException("Anda bukan pemilik kosan ini");
        }

        kosanRepository.delete(kosan);
    }

    public List<KosanResponse> getMyListings(User pemilik) {
        return kosanRepository.findByPemilikId(pemilik.getId())
                .stream()
                .map(kosanMapper::toKosanResponse)
                .collect(Collectors.toList());
    }
}