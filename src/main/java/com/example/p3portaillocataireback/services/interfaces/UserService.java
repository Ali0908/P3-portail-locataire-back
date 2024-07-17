package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.response.UserResponseDto;

import java.util.Optional;

public interface UserService {
    /**
     * Get rental by id
     *
     * @param id {@link Integer}
     * @return {@link UserResponseDto}
     */
    Optional<UserResponseDto> getUserById(Integer id);
}
