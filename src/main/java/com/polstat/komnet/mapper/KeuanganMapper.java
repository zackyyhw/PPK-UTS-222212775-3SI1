package com.polstat.komnet.mapper;

import com.polstat.komnet.dto.KeuanganDto;
import com.polstat.komnet.entity.Keuangan;
import com.polstat.komnet.entity.User;
import com.polstat.komnet.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class KeuanganMapper {

    private final UserRepository memberRepository;

    public KeuanganMapper(UserRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Keuangan mapToKeuangan(KeuanganDto keuanganDto) {
        User bendahara = memberRepository.findById(keuanganDto.getBendaharaId())
                .orElseThrow(() -> new RuntimeException("Bendahara tidak ditemukan"));
        return Keuangan.builder()
                .id(keuanganDto.getId())
                .jumlah(keuanganDto.getJumlah())
                .jenisTransaksi(keuanganDto.getJenisTransaksi())
                .deskripsi(keuanganDto.getDeskripsi())
                .tanggalTransaksi(keuanganDto.getTanggalTransaksi())
                .bendahara(bendahara)
                .build();
    }

    public KeuanganDto mapToKeuanganDto(Keuangan keuangan) {
        return KeuanganDto.builder()
                .id(keuangan.getId())
                .jumlah(keuangan.getJumlah())
                .jenisTransaksi(keuangan.getJenisTransaksi())
                .deskripsi(keuangan.getDeskripsi())
                .tanggalTransaksi(keuangan.getTanggalTransaksi())
                .bendaharaId(keuangan.getBendahara() != null ? keuangan.getBendahara().getId() : null)
                .build();
    }
}
