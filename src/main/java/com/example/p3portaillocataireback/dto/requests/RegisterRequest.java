package com.example.p3portaillocataireback.dto.requests;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
