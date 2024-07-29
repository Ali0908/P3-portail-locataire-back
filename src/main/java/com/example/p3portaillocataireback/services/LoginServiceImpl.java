package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.LoginRequest;
import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.response.LoginResponse;
import com.example.p3portaillocataireback.dto.response.UserResponseDto;
import com.example.p3portaillocataireback.services.interfaces.LoginService;
import com.example.p3portaillocataireback.entity.Token;
import com.example.p3portaillocataireback.repository.TokenRepository;
import com.example.p3portaillocataireback.configuration.TokenType;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    public Optional<LoginResponse> register(RegisterRequest request) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String text = date.format(formatter);
        LocalDate parsedDate = LocalDate. parse(text, formatter);

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .created_at(parsedDate)
                .updated_at(parsedDate)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtServiceImpl.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return Optional.of(LoginResponse.builder()
                .token(jwtToken)
                .build());
    }


    public Optional<LoginResponse> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtServiceImpl.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return Optional.of(LoginResponse.builder()
                .token(jwtToken)
                .build());
    }

    public Optional<UserResponseDto> authenticate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreated_at(),
                user.getUpdated_at()
        ));

    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
