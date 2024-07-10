package com.example.p3portaillocataireback.dto.response;

public record RentalResponseDto(
        Integer id,
        String name,
        Float surface,
        Float price,
        String picture,
        String description
) {
}
