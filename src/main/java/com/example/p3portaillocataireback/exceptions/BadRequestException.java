package com.example.p3portaillocataireback.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BadRequestException extends RuntimeException{
    private String message;
}
