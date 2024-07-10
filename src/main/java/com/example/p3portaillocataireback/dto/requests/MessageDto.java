package com.example.p3portaillocataireback.dto.requests;

import lombok.Getter;

public record MessageDto(
        @Getter String messages,
        @Getter Integer rental_id,
        @Getter Integer user_id
) {
}
