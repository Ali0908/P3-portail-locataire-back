package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.entity.User;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;


@Service
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
       String createdAt = user.getCreated_at().format(formatter);
       String updatedAt = user.getUpdated_at().format(formatter);

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                createdAt,
                updatedAt
        );
    }
}
