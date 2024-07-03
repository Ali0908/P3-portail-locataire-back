package com.example.p3portaillocataireback.rentals;

import lombok.Getter;


public record RentalsDto(
        @Getter String name,
        @Getter Float surface,
        @Getter Float price,
        @Getter String picture,
        @Getter String description

) {
}