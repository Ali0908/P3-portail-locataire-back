package com.example.p3portaillocataireback.dto.requests;

import lombok.Getter;

import java.sql.Timestamp;

public record RentalUpdateDto(
        @Getter
        String name,
        @Getter
        Float surface,
        @Getter
        Float price,
        @Getter
        String description,
        @Getter
        Timestamp created_at,
        @Getter
        Timestamp updated_at,
        @Getter
        Integer owner_id
) {
}
