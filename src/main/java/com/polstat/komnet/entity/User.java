package com.polstat.komnet.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String nim;
    private String kelas;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;  // Peran pengguna: ADMIN, BENDAHARA, ANGGOTA

    private String statusPembayaranKas;  // Default : "Belum Diinput", Contoh Pengisian : "Sudah Bayar" / "Belum Bayar"

    public enum Role {
        ADMIN, BENDAHARA, ANGGOTA
    }
}
