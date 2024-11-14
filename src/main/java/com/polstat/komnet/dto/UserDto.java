package com.polstat.komnet.dto;

import com.polstat.komnet.entity.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private Long id;
    private String nama;
    private String nim;
    private String kelas;
    private String email;
    private String password;
    private Role role;  // Role yang dimiliki oleh User
    private String statusPembayaranKas;

    // Mengembalikan authorities (roles) yang dimiliki User
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mengonversi role menjadi SimpleGrantedAuthority
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;  // Username berdasarkan email
    }

    @Override
    public String getPassword() {
        return this.password;  // Password
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Akun selalu aktif
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Akun selalu tidak terkunci
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Kredensial selalu valid
    }

    @Override
    public boolean isEnabled() {
        return true;  // Akun selalu diaktifkan
    }
}
