package com.example.p3portaillocataireback.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public record RentalDto(
        @Getter
        @NotBlank(message = "Name is required")
        String name,
        @Getter
        @NotBlank(message = "Surface is required")
        Float surface,
        @Getter
        @NotBlank(message = "Price is required")
        Float price,
        @Getter
        @NotBlank(message = "Picture is required")
        String picture,
        @Getter
        @NotBlank(message = "Description is required")
        @Max(value = 255, message = "Description is too long")
        String description,
        @Getter
        String created_at,
        @Getter
        String updated_at,
        @Getter
        Integer owner_id
) {
}