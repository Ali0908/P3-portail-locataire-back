package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.requests.MessageDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;

import java.util.Optional;

public interface MessageService {
    /**
     * Create new message
     *
     * @param messageDto {@link MessageDto}
     * @return {@link MessageResponseDto}
     */
     Optional<MessageResponseDto> create(MessageDto messageDto);
}
