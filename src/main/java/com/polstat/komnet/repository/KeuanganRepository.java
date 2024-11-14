package com.polstat.komnet.repository;

import com.polstat.komnet.entity.Keuangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface KeuanganRepository extends JpaRepository<Keuangan, Long> {
    // Mencari transaksi keuangan berdasarkan jenis transaksi (masuk/keluar)
    List<Keuangan> findByJenisTransaksi(String jenisTransaksi);

    // Mencari transaksi keuangan berdasarkan rentang tanggal
    List<Keuangan> findByTanggalTransaksiBetween(LocalDate startDate, LocalDate endDate);
}
