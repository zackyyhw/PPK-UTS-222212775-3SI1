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
@Table(name = "informasi")
public class Informasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaAktivitas;
    private String deskripsi;
    private LocalDate tanggalAcara;
    private String status;  // "Aktif" / "Selesai"
}
