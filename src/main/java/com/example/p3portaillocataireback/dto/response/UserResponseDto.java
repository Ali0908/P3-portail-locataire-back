package com.example.p3portaillocataireback.dto.response;


import java.time.LocalDate;

public record UserResponseDto(
        Integer id,
        String name,
        String email,
        LocalDate created_at,
        LocalDate updated_at
) {
}
