package com.polstat.komnet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "keuangan")
public class Keuangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double jumlah;  
    private String jenisTransaksi;  
    private String deskripsi;
    private LocalDate tanggalTransaksi;

    @ManyToOne
    @JoinColumn(name = "bendahara_id", referencedColumnName = "id")
    private User bendahara;  // Menghubungkan transaksi dengan bendahara
}

