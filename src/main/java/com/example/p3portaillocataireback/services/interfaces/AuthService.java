package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.requests.AuthRequest;
import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.response.AuthResponse;

import java.util.Optional;

public interface AuthService {

    /**
     * Register a new user
     *
     * @param request {@link RegisterRequest}
     * @return {@link AuthResponse}
     */
    public Optional<AuthResponse> register(RegisterRequest request);

    /**
     * Authenticate a user
     *
     * @param request {@link AuthRequest}
     * @return {@link AuthResponse}
     */
    public Optional<AuthResponse> authenticate(AuthRequest request);
}
