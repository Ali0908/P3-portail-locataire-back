package com.example.p3portaillocataireback.dto.response;


public record UserResponseDto(
        Integer id,
        String name,
        String email,
        String created_at,
        String updated_at
) {
}
