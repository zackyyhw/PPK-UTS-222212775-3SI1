package com.polstat.komnet.controller;

import com.polstat.komnet.dto.InformasiDto;
import com.polstat.komnet.service.InformasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/informasi")
public class InformasiController {

    @Autowired
    private InformasiService informasiService;

    @Operation(
        summary = "Menambah acara baru",
        description = "Endpoint ini digunakan untuk menambah acara baru.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Acara berhasil ditambahkan",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
            )
        }
    )
    @PostMapping("/addEventInfo")
    public ResponseEntity<String> createEvent(@RequestBody InformasiDto informasiDto) {
        informasiService.createEvent(informasiDto);
        return ResponseEntity.ok("Acara berhasil ditambahkan!");
    }

    @Operation(
        summary = "Mendapatkan semua acara",
        description = "Endpoint ini digunakan untuk mendapatkan daftar semua acara yang ada.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Daftar acara berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformasiDto.class))
            )
        }
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<InformasiDto>> getAllEvents() {
        return ResponseEntity.ok(informasiService.getAllEvents());
    }

    @Operation(
        summary = "Mencari acara berdasarkan status",
        description = "Endpoint ini digunakan untuk mencari acara berdasarkan status tertentu.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Daftar acara berdasarkan status ditemukan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformasiDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ditemukan acara dengan status yang diminta",
                content = @Content
            )
        }
    )
    @GetMapping("/searchByStatus")
    public ResponseEntity<List<InformasiDto>> getEventsByStatus(@RequestParam String status) {
        List<InformasiDto> events = informasiService.getEventsByStatus(status);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @Operation(
        summary = "Mencari acara berdasarkan nama aktivitas",
        description = "Endpoint ini digunakan untuk mencari acara berdasarkan nama aktivitas.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Daftar acara berdasarkan nama aktivitas ditemukan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformasiDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ditemukan acara dengan nama aktivitas yang diminta",
                content = @Content
            )
        }
    )
    @GetMapping("/searchByNamaAktivitas")
    public ResponseEntity<List<InformasiDto>> getEventsByNamaAktivitas(@RequestParam String namaAktivitas) {
        List<InformasiDto> events = informasiService.getEventsByNamaAktivitas(namaAktivitas);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @Operation(
        summary = "Mencari acara berdasarkan rentang tanggal acara",
        description = "Endpoint ini digunakan untuk mencari acara berdasarkan rentang tanggal tertentu.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Daftar acara berdasarkan rentang tanggal ditemukan",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformasiDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ditemukan acara dalam rentang tanggal yang diminta",
                content = @Content
            )
        }
    )
    @GetMapping("/searchByTanggalAcara")
    public ResponseEntity<List<InformasiDto>> getEventsByTanggalAcara(
            @RequestParam String startDate, @RequestParam String endDate) {
        
        // Konversi tanggal dari string ke LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        List<InformasiDto> informasiDtos = informasiService.getEventsByTanggalAcara(start, end);
        if (informasiDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(informasiDtos);
    }
}
