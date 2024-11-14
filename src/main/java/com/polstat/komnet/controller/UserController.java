package com.polstat.komnet.controller;

import com.polstat.komnet.dto.UserDto;
import com.polstat.komnet.service.UserService;
import com.polstat.komnet.dto.ChangePasswordRequestDto;
import com.polstat.komnet.repository.UserRepository;
import com.polstat.komnet.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(
        summary = "Mengambil daftar pengguna berdasarkan nama",
        description = "Mendapatkan daftar pengguna yang difilter berdasarkan nama mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ada pengguna ditemukan dengan nama yang diberikan",
                content = @Content
            )
        }
    )
    @GetMapping("/nama")
    public ResponseEntity<List<UserDto>> getUsersByNama(@RequestParam String nama) {
        List<UserDto> userDtos = userService.getUsersByNama(nama);
        return userDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
        summary = "Mengambil daftar pengguna berdasarkan NIM",
        description = "Mendapatkan daftar pengguna yang difilter berdasarkan NIM mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ada pengguna ditemukan dengan NIM yang diberikan",
                content = @Content
            )
        }
    )
    @GetMapping("/nim")
    public ResponseEntity<List<UserDto>> getUsersByNim(@RequestParam String nim) {
        List<UserDto> userDtos = userService.getUsersByNim(nim);
        return userDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
        summary = "Mengambil daftar pengguna berdasarkan kelas",
        description = "Mendapatkan daftar pengguna yang difilter berdasarkan kelas mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ada pengguna ditemukan dengan kelas yang diberikan",
                content = @Content
            )
        }
    )
    @GetMapping("/kelas")
    public ResponseEntity<List<UserDto>> getUsersByKelas(@RequestParam String kelas) {
        List<UserDto> userDtos = userService.getUsersByKelas(kelas);
        return userDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
        summary = "Mengambil daftar pengguna berdasarkan email",
        description = "Mendapatkan daftar pengguna yang difilter berdasarkan email mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ada pengguna ditemukan dengan email yang diberikan",
                content = @Content
            )
        }
    )
    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> getUsersByEmail(@RequestParam String email) {
        List<UserDto> userDtos = userService.getUsersByEmail(email);
        return userDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
        summary = "Mengambil daftar pengguna berdasarkan status pembayaran kas",
        description = "Mendapatkan daftar pengguna yang difilter berdasarkan status pembayaran kas mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tidak ada pengguna ditemukan dengan status pembayaran kas yang diberikan",
                content = @Content
            )
        }
    )
    @GetMapping("/status-kas")
    public ResponseEntity<List<UserDto>> getUsersByStatusPembayaranKas(@RequestParam String statusPembayaranKas) {
        List<UserDto> userDtos = userService.getUsersByStatusPembayaranKas(statusPembayaranKas);
        return userDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
        summary = "Mengambil semua pengguna",
        description = "Mendapatkan daftar semua pengguna di sistem.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Semua pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            )
        }
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Mengambil pengguna berdasarkan ID",
        description = "Mendapatkan pengguna tertentu berdasarkan ID mereka.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diambil",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Pengguna tidak ditemukan",
                content = @Content
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
        summary = "Memperbarui pengguna",
        description = "Memperbarui detail pengguna yang ada, khusus untuk ID yang sedang login.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil diperbarui",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Dilarang: pengguna tidak dapat memperbarui pengguna ini",
                content = @Content
            )
        }
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('BENDAHARA') or hasRole('ANGGOTA') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
        summary = "Mengubah kata sandi pengguna",
        description = "Mengubah kata sandi pengguna, khusus untuk ID yang sedang login",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Kata sandi berhasil diperbarui",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Password berhasil diubah"))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Permintaan tidak valid: data tidak valid",
                content = @Content
            )
        }
    )
    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto request) {
        userService.changePassword(id, request);
        return ResponseEntity.ok("Password berhasil diubah");
    }

    @Operation(
        summary = "Menghapus pengguna",
        description = "Menghapus pengguna yang ada berdasarkan ID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pengguna berhasil dihapus",
                content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "User berhasil dihapus"))
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Dilarang: pengguna tidak dapat menghapus pengguna ini",
                content = @Content
            )
        }
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('BENDAHARA') or hasRole('ANGGOTA') or #id == authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User berhasil dihapus");
    }
}
