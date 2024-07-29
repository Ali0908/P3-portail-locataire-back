package com.example.p3portaillocataireback.dto.requests;

import lombok.Getter;

import java.time.LocalDate;

public record UserDto(
        @Getter
        Integer id,
        @Getter
        String name,
        @Getter
        String email,
        @Getter
        LocalDate created_at,
        @Getter
        LocalDate updated_at

) {
}
