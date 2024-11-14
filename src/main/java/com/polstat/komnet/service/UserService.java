package com.polstat.komnet.service;

import com.polstat.komnet.dto.UserDto;
import com.polstat.komnet.entity.User;
import com.polstat.komnet.repository.UserRepository;
import com.polstat.komnet.mapper.UserMapper;
import com.polstat.komnet.dto.ChangePasswordRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(UserMapper.mapToUser(userDto));
        return UserMapper.mapToUserDto(user);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Mengambil data terbaru dari user yang diupdate
        return UserMapper.mapToUserDto(user);
    }

    public List<UserDto> getUsersByNama(String nama) {
        List<User> users = userRepository.findByNamaContaining(nama);
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByNim(String nim) {
        List<User> users = userRepository.findByNimContaining(nim);
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByKelas(String kelas) {
        List<User> users = userRepository.findByKelas(kelas);
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByEmail(String email) {
        List<User> users = userRepository.findByEmailContaining(email);
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByStatusPembayaranKas(String statusPembayaranKas) {
        List<User> users = userRepository.findByStatusPembayaranKas(statusPembayaranKas);
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        // Mendapatkan ID pengguna yang sedang login dari SecurityContext
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findOptionalByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        // Pastikan pengguna yang sedang login hanya bisa mengubah dirinya sendiri
        if (!currentUser.getId().equals(id)) {
            throw new RuntimeException("Anda tidak memiliki izin untuk mengubah data pengguna lain");
        }
    
        // Jika ID valid, lanjutkan dengan proses update
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        user.setNama(userDto.getNama());
        user.setNim(userDto.getNim());
        user.setKelas(userDto.getKelas());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
    
        // Simpan perubahan dan pastikan sudah terupdate
        userRepository.save(user);
    
        return UserMapper.mapToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public void changePassword(Long id, ChangePasswordRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Periksa apakah password lama benar
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Password lama tidak sesuai");
        }

        // Setel password baru
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // Mendapatkan ID pengguna yang sedang login dari SecurityContext
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findOptionalByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        // Pastikan pengguna yang sedang login hanya bisa menghapus dirinya sendiri
        if (!currentUser.getId().equals(id)) {
            throw new RuntimeException("Anda tidak memiliki izin untuk menghapus data pengguna lain");
        }
    
        // Jika ID valid, lanjutkan dengan proses hapus
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    
        userRepository.delete(user);
    }
}
