package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.MessageDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.entity.Message;
import com.example.p3portaillocataireback.mapper.MessageMapper;
import com.example.p3portaillocataireback.repository.MessageRepository;
import com.example.p3portaillocataireback.services.interfaces.MessageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }
    public Optional<MessageResponseDto> create(MessageDto messageDto) {
        var message = messageMapper.toMessage(messageDto);
        messageRepository.save(message);
        return Optional.of(messageMapper.toMessageResponseDto(message));
    }
}
