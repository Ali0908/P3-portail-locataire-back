package com.example.p3portaillocataireback.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Email is not valid")
    @NotBlank(message = "error")
    private String email;
    @NotBlank(message = "error")
    String password;
}
