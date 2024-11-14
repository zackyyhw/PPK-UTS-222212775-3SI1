package com.polstat.komnet.auth;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthRequest {
    @Size(max = 100, message = "Email must be less than or equal to 100 characters.")
    private String email;

    @Size(max = 20, message = "Password must be less than or equal to 20 characters.")
    private String password;
}
