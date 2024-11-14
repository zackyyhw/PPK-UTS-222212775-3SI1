package com.polstat.komnet.mapper;

import com.polstat.komnet.dto.InformasiDto;
import com.polstat.komnet.entity.Informasi;

public class InformasiMapper {
    public static Informasi mapToInformasi(InformasiDto informasiDto) {
        return Informasi.builder()
                .id(informasiDto.getId())
                .namaAktivitas(informasiDto.getNamaAktivitas())
                .deskripsi(informasiDto.getDeskripsi())
                .status(informasiDto.getStatus())
                .tanggalAcara(informasiDto.getTanggalAcara()) 
                .build();
    }

    public static InformasiDto mapToInformasiDTO(Informasi informasi) {
        return InformasiDto.builder()
                .id(informasi.getId())
                .namaAktivitas(informasi.getNamaAktivitas())
                .deskripsi(informasi.getDeskripsi())
                .status(informasi.getStatus())
                .tanggalAcara(informasi.getTanggalAcara()) 
                .build();
    }
}

