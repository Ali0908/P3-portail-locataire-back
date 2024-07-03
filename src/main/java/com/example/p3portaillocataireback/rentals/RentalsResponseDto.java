package com.example.p3portaillocataireback.rentals;

public record RentalsResponseDto(
        Integer id,
        String name,
        Float surface,
        Float price,
        String picture,
        String description
) {
}
