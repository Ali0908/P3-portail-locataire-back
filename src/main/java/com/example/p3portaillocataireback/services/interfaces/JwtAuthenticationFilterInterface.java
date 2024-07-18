package com.example.p3portaillocataireback.services.interfaces;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface JwtAuthenticationFilterInterface {
    void doFilterInternal(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain) throws ServletException, IOException;
}
