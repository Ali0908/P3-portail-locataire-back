package com.example.p3portaillocataireback.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record MessageDto(
        @Getter
         @NotBlank
        String message,
        @Getter
        @NotNull
        Integer rental_id,
        @Getter
        @NotNull
        Integer user_id
) {
}
