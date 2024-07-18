package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.requests.LoginRequest;
import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.response.LoginResponse;
import com.example.p3portaillocataireback.dto.response.UserResponseDto;

import java.util.Optional;

public interface LoginService {

    /**
     * Register a new user
     *
     * @param request {@link RegisterRequest}
     * @return {@link LoginResponse}
     */
     Optional<LoginResponse> register(RegisterRequest request);

    /**
     * Log a user
     *
     * @param request {@link LoginRequest}
     * @return {@link LoginResponse}
     */
     Optional<LoginResponse> login(LoginRequest request);

     /**
      * Get the current user
      *
      * @return {@link UserResponseDto}
      */
      Optional<UserResponseDto> authenticate();
}
