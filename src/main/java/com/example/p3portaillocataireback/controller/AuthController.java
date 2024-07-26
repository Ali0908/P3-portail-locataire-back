package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.LoginRequest;
import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.response.LoginResponse;
import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.exceptions.BadRequestException;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.services.interfaces.LoginService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService service;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<LoginResponse> register(@RequestBody @Validated RegisterRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(errorMessage);
        }
        return service.register(request);
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:3001")
    public Optional<LoginResponse> login(
            @RequestBody @Validated LoginRequest request, BindingResult result
    ) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new UnauthorizedRequestException(errorMessage);
        }
        return service.login(request);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public Optional<UserResponseDto> authenticate(Principal user) {
        System.out.println(user);
        return service.authenticate();
    }

}
