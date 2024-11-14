package com.polstat.komnet.service;

import com.polstat.komnet.entity.User;
import com.polstat.komnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    
        // Menambahkan prefiks ROLE_ ke role
        String roleWithPrefix = "ROLE_" + user.getRole().name();
    
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // menggunakan email sebagai username
                .password(user.getPassword()) // password user
                .authorities(new SimpleGrantedAuthority(roleWithPrefix)) // Menambahkan ROLE_ prefiks
                .build();
    }

}
