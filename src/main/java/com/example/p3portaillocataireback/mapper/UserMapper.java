package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreated_at(),
                user.getUpdated_at()
        );
    }
}
