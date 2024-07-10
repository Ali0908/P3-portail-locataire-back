package com.example.p3portaillocataireback.exceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnauthorizedRequestException extends RuntimeException{
    private final String message;
}
