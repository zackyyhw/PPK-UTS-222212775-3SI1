package com.polstat.komnet.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (!hasAuthorizationBearer(request)) {
            System.out.println("Tidak ada Authorization Bearer di request.");
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = getAccessToken(request);
        System.out.println("Token yang diterima: " + token);  

        if (!jwtUtil.validateAccessToken(token)) {
            System.out.println("Token tidak valid!");
            filterChain.doFilter(request, response);
            return;
        }
        
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        if (userDetails != null) {
            System.out.println("Membuat konteks autentikasi untuk user: " + userDetails.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println("UserDetails tidak ditemukan untuk token ini.");
        }
    }
    
    private UserDetails getUserDetails(String token) {
        String email = jwtUtil.getSubject(token); // Mendapatkan email dari token
        String role = jwtUtil.getClaim(token, "role"); // Mendapatkan role dari token
        
        if (role != null && !role.startsWith("ROLE_")) {
            role = "ROLE_" + role; 
        }
    
        // Debug role yang diterima
        System.out.println("Role dari token: " + role);
        
        if (email == null || role == null) {
            System.out.println("Email atau role tidak ditemukan dalam token!");
            return null;
        }
        
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        
        System.out.println("Membuat UserDetails dengan email: " + email + " dan role: " + role);
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .authorities(authorities)
                .password("") // Password kosong, karena tidak digunakan dalam konteks ini
                .build();
    }
    
}
