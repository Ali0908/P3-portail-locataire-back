package com.example.p3portaillocataireback.dto.requests;

import com.example.p3portaillocataireback.configuration.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private Role role;

    public RegisterRequest(String message) {
    }
}
