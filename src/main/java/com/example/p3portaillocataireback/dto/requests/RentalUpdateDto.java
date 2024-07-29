package com.example.p3portaillocataireback.dto.requests;

import lombok.Getter;


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
        String created_at,
        @Getter
        String updated_at,
        @Getter
        Integer owner_id
) {
}
