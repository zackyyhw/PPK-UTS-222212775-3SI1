package com.polstat.komnet.repository;

import com.polstat.komnet.entity.Informasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InformasiRepository extends JpaRepository<Informasi, Long> {

    // Mencari informasi berdasarkan status (Aktif/Selesai)
    List<Informasi> findByStatus(String status);

    // Mencari informasi berdasarkan nama aktivitas
    List<Informasi> findByNamaAktivitasContaining(String namaAktivitas);

    // Mencari informasi berdasarkan rentang tanggal acara
    List<Informasi> findByTanggalAcaraBetween(LocalDate startDate, LocalDate endDate);
}
