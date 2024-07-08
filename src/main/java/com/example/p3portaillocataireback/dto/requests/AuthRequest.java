package com.example.p3portaillocataireback.dto.requests;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String email;
    @NotBlank(message = "Password is required")
    String password;
    @NotBlank(message = "Username is required")
    String username;
}
