package com.example.p3portaillocataireback.controller.advice;

import com.example.p3portaillocataireback.dto.response.LoginResponseFailed;
import com.example.p3portaillocataireback.exceptions.BadRequestException;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<LoginResponseFailed> handleUnauthorizedRequestException(UnauthorizedRequestException e) {
        LoginResponseFailed authResponse = LoginResponseFailed.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
    }
}
