package com.example.p3portaillocataireback.dto.response;

import java.sql.Timestamp;

public record UserResponseDto(
        Integer id,
        String name,
        String email,
        Timestamp created_at,
        Timestamp updated_at
) {
}
