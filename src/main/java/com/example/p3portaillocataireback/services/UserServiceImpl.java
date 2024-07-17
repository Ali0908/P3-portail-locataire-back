package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.mapper.UserMapper;
import com.example.p3portaillocataireback.repository.UserRepository;
import com.example.p3portaillocataireback.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
    public Optional<UserResponseDto> getUserById(Integer id) {
        return Optional.of((userRepository.findById(id)
                .map(userMapper::toUserResponseDto)
                .orElseThrow()));
    }
}
