package com.polstat.komnet.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class KeuanganDto {
    private Long id;

    @NotNull(message = "Jenis transaksi wajib diisi.")
    private String jenisTransaksi;  // "masuk" atau "keluar"
    
    private double jumlah;  // Jumlah uang
    
    private LocalDate tanggalTransaksi;  // Tanggal transaksi

    private String deskripsi; //deskripsi pemasukan ataupun pengeluaran

    private Long bendaharaId; //penanda bendahara yang melakukan input pencatatan
}
