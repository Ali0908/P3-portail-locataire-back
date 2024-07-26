package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userSrv;
    @GetMapping("/{user-id}")
    @SecurityRequirement(name = "bearerAuth")
    public UserResponseDto getUserById(@PathVariable("user-id") Integer id) {
        return userSrv.getUserById(id).orElseThrow(() -> new UnauthorizedRequestException("Unauthorized request"));
    }
}
