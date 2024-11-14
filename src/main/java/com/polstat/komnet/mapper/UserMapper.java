package com.polstat.komnet.mapper;

import com.polstat.komnet.dto.UserDto;
import com.polstat.komnet.entity.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nama(user.getNama())
                .nim(user.getNim())
                .kelas(user.getKelas())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .statusPembayaranKas(user.getStatusPembayaranKas() != null ? user.getStatusPembayaranKas() : "Belum Diinput")
                .build();
    }

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .nama(userDto.getNama())
                .nim(userDto.getNim())
                .kelas(userDto.getKelas())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .statusPembayaranKas(userDto.getStatusPembayaranKas() != null ? userDto.getStatusPembayaranKas() : "Belum Diinput")
                .build();
    }
}
