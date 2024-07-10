package com.example.p3portaillocataireback.dto.response;

public record MessageResponseDto(
        String messages,
        Integer rental_id,
        Integer user_id
) {
}
