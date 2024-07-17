package com.example.p3portaillocataireback.dto.requests;

import lombok.Getter;

import java.sql.Timestamp;

public record UserDto(
        @Getter
        Integer id,
        @Getter
        String name,
        @Getter
        String email,
        @Getter
        Timestamp created_at,
        @Getter
        Timestamp updated_at

) {
}
