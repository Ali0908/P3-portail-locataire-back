package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.MessageDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.exceptions.BadRequestException;
import com.example.p3portaillocataireback.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages/")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageSrv;

    @PostMapping()
    public MessageResponseDto create(
            @RequestBody MessageDto messageDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(errorMessage);
        }
        return messageSrv.create(messageDto).get();
    }
}