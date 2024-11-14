package com.polstat.komnet.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class InformasiDto {
    private Long id;

    @NotEmpty(message = "Judul informasi wajib diisi.")
    private String namaAktivitas;

    private String deskripsi;

    private String status;  // Misalnya: "terdaftar", "selesai"

    private LocalDate tanggalAcara;
}
