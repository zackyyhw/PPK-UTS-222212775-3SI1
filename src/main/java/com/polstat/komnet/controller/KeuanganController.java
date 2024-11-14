package com.polstat.komnet.controller;

import com.polstat.komnet.dto.KeuanganDto;
import com.polstat.komnet.service.KeuanganService;
import com.polstat.komnet.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/keuangan")
public class KeuanganController {

    @Autowired
    private KeuanganService keuanganService;

    @Operation(
        summary = "Menambah transaksi keuangan baru",
        description = "Endpoint ini digunakan untuk menambah transaksi keuangan baru oleh BENDAHARA atau ADMIN.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Transaksi keuangan berhasil ditambahkan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeuanganDto.class))
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Akses ditolak, hanya BENDAHARA atau ADMIN yang dapat mengakses",
                content = @Content
            )
        }
    )
    @PreAuthorize("hasRole('BENDAHARA') or hasRole('ADMIN')")
    @PostMapping("/addTransaksi")
    public ResponseEntity<KeuanganDto> createKeuangan(@RequestBody KeuanganDto keuanganDto) {
        KeuanganDto createdKeuangan = keuanganService.createKeuangan(keuanganDto);
        return ResponseEntity.ok(createdKeuangan);
    }

    @Operation(
        summary = "Mencari transaksi keuangan berdasarkan jenis transaksi",
        description = "Endpoint ini digunakan untuk mencari transaksi keuangan berdasarkan jenis transaksi.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Transaksi keuangan ditemukan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeuanganDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ditemukan transaksi dengan jenis transaksi yang diminta",
                content = @Content
            )
        }
    )
    @GetMapping("/searchByJenisTransaksi")
    public ResponseEntity<List<KeuanganDto>> getKeuanganByJenisTransaksi(@RequestParam String jenisTransaksi) {
        List<KeuanganDto> keuanganDtos = keuanganService.getKeuanganByJenisTransaksi(jenisTransaksi);
        if (keuanganDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(keuanganDtos);
    }

    @Operation(
        summary = "Mencari transaksi keuangan berdasarkan tanggal transaksi",
        description = "Endpoint ini digunakan untuk mencari transaksi keuangan berdasarkan rentang tanggal transaksi.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Transaksi keuangan ditemukan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeuanganDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ditemukan transaksi pada rentang tanggal yang diminta",
                content = @Content
            )
        }
    )
    @GetMapping("/searchByTanggalTransaksi")
    public ResponseEntity<List<KeuanganDto>> getKeuanganByTanggalTransaksi(
            @RequestParam String startDate, @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<KeuanganDto> keuanganDtos = keuanganService.getKeuanganByTanggalTransaksi(start, end);
        if (keuanganDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(keuanganDtos);
    }

    @Operation(
        summary = "Mendapatkan semua transaksi keuangan",
        description = "Endpoint ini digunakan untuk mendapatkan daftar semua transaksi keuangan.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Daftar semua transaksi keuangan berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeuanganDto.class))
            )
        }
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<KeuanganDto>> getAllKeuangan() {
        List<KeuanganDto> keuanganDtos = keuanganService.getAllKeuangan();
        return ResponseEntity.ok(keuanganDtos);
    }

    @Operation(
        summary = "Mengupdate status pembayaran kas",
        description = "Endpoint ini digunakan oleh BENDAHARA untuk mengupdate status pembayaran kas pengguna.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Status pembayaran kas berhasil diperbarui",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Akses ditolak, hanya BENDAHARA atau ADMIN yang dapat mengakses",
                content = @Content
            )
        }
    )
    @PreAuthorize("hasRole('BENDAHARA') or hasRole('ADMIN')")
    @PatchMapping("/updateStatusPembayaran/{userId}")
    public ResponseEntity<User> updateStatusPembayaranKas(
            @PathVariable Long userId, @RequestParam String statusPembayaranKas) {
        User updatedUser = keuanganService.updateStatusPembayaranKas(userId, statusPembayaranKas);
        return ResponseEntity.ok(updatedUser);
    }
}
