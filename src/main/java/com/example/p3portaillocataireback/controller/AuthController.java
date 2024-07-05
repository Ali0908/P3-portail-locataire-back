package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.AuthRequest;
import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.response.AuthResponse;
import com.example.p3portaillocataireback.exceptions.BadRequestException;
import com.example.p3portaillocataireback.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody @Validated RegisterRequest request, BindingResult result

    ) {
        if (result.hasErrors()) {
            throw new BadRequestException("Error");
            // Il faut que l'erreur contiennent le message
            // Créer des exceptions personnalisées
        }
        return service.register(request).get();
    }

    @PostMapping("/login")
    public AuthResponse authenticate(
            @RequestBody @Validated AuthRequest request, BindingResult result
    ) {
        return service.authenticate(request).get();
    }
}
