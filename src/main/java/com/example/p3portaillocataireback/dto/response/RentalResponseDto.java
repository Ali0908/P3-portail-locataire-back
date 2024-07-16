package com.example.p3portaillocataireback.dto.response;

import java.sql.Timestamp;

public record RentalResponseDto(
        Integer id,
        String name,
        Float surface,
        Float price,
        String picture,
        String description,
        Timestamp created_at,
        Timestamp updated_at,
        Integer owner_id
) {
}