package com.example.p3portaillocataireback.dto.response;

import lombok.Getter;


public record RentalResponseDto(
        Integer id,
        String name,
        Float surface,
        Float price,
        String picture,
        String description,
        @Getter
        String created_at,
        String updated_at,
        Integer owner_id
) {
}