package com.example.p3portaillocataireback.controller.advice;

import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ExceptionHandler(BadRequestException.class);
@ControllerAdvice
public class GlobalExceptionHandler {


    public ResponseEntity<RegisterRequest> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new RegisterRequest(e.getMessage()));
    }
}
