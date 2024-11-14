package com.polstat.komnet.service;

import com.polstat.komnet.dto.KeuanganDto;
import com.polstat.komnet.entity.Keuangan;
import com.polstat.komnet.entity.User;
import com.polstat.komnet.repository.KeuanganRepository;
import com.polstat.komnet.repository.UserRepository;
import com.polstat.komnet.mapper.KeuanganMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeuanganService {

    @Autowired
    private KeuanganRepository keuanganRepository;

    @Autowired
    private KeuanganMapper keuanganMapper;

    @Autowired
    private UserRepository userRepository;

    // Menambahkan transaksi keuangan baru
    public KeuanganDto createKeuangan(KeuanganDto keuanganDto) {
        Keuangan keuangan = keuanganMapper.mapToKeuangan(keuanganDto);
        keuanganRepository.save(keuangan);
        return keuanganMapper.mapToKeuanganDto(keuangan);
    }

    // Mencari transaksi berdasarkan jenis transaksi (masuk/keluar)
    public List<KeuanganDto> getKeuanganByJenisTransaksi(String jenisTransaksi) {
        List<Keuangan> keuangan = keuanganRepository.findByJenisTransaksi(jenisTransaksi);
        return keuangan.stream()
                .map(keuanganMapper::mapToKeuanganDto)
                .collect(Collectors.toList());
    }

    // Mencari transaksi berdasarkan rentang tanggal
    public List<KeuanganDto> getKeuanganByTanggalTransaksi(LocalDate startDate, LocalDate endDate) {
        List<Keuangan> keuangans = keuanganRepository.findByTanggalTransaksiBetween(startDate, endDate);
        return keuangans.stream()
                .map(keuanganMapper::mapToKeuanganDto)
                .collect(Collectors.toList());
    }

    // Mengambil semua transaksi
    public List<KeuanganDto> getAllKeuangan() {
        List<Keuangan> keuangans = keuanganRepository.findAll();
        return keuangans.stream()
                .map(keuanganMapper::mapToKeuanganDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public User updateStatusPembayaranKas(Long userId, String statusPembayaranKas) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatusPembayaranKas(statusPembayaranKas);
        userRepository.save(user); // Pastikan data disimpan
        return userRepository.save(user);
    }
}
