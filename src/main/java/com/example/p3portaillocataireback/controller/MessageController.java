package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.MessageDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.services.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageServiceImpl messageServiceImpl;

    @PostMapping()
    public MessageResponseDto create(
            @RequestBody MessageDto messageDto) {
        return messageServiceImpl.create(messageDto).get();
    }
}