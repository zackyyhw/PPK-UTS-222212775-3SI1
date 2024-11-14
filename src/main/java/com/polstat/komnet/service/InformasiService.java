package com.polstat.komnet.service;

import com.polstat.komnet.dto.InformasiDto;
import com.polstat.komnet.entity.Informasi;
import com.polstat.komnet.mapper.InformasiMapper;
import com.polstat.komnet.repository.InformasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InformasiService {

    @Autowired
    private InformasiRepository informasiRepository;

    // Menambahkan acara
    public void createEvent(InformasiDto informasiDto) {
        Informasi informasi = InformasiMapper.mapToInformasi(informasiDto);
        informasiRepository.save(informasi);
    }

    // Mendapatkan semua acara
    public List<InformasiDto> getAllEvents() {
        return informasiRepository.findAll().stream()
                .map(InformasiMapper::mapToInformasiDTO)
                .collect(Collectors.toList());
    }

    // Mendapatkan acara berdasarkan status (Aktif/Selesai)
    public List<InformasiDto> getEventsByStatus(String status) {
        return informasiRepository.findByStatus(status).stream()
                .map(InformasiMapper::mapToInformasiDTO)
                .collect(Collectors.toList());
    }

    // Mendapatkan acara berdasarkan nama aktivitas
    public List<InformasiDto> getEventsByNamaAktivitas(String namaAktivitas) {
        return informasiRepository.findByNamaAktivitasContaining(namaAktivitas).stream()
                .map(InformasiMapper::mapToInformasiDTO)
                .collect(Collectors.toList());
    }

    // Mendapatkan acara berdasarkan rentang tanggal acara
    public List<InformasiDto> getEventsByTanggalAcara(LocalDate startDate, LocalDate endDate) {
        // Mengambil data acara berdasarkan rentang tanggal
        List<Informasi> events = informasiRepository.findByTanggalAcaraBetween(startDate, endDate);
        return events.stream()
                .map(InformasiMapper::mapToInformasiDTO)
                .collect(Collectors.toList());
    }

}
