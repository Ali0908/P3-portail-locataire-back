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

        // Formatage des dates
        // Création d'un utilisateur
        var user = User.builder()
                //  Conversion du nom d'utilisateur en minuscules
                .name(request.getName())
                // Conversion de l'email en minuscules
                .email(request.getEmail())
                // Encodage du mot de passe
                .password(passwordEncoder.encode(request.getPassword()))
                .created_at(LocalDate.now())
                .updated_at(LocalDate.now())
                .build();
        // Enregistrement de l'utilisateur en base de données
        var savedUser = repository.save(user);
        // Génération du jeton d'accès
        var jwtToken = jwtServiceImpl.generateToken(user);
        // Sauvegarde du jeton d'accès pour l'utilisateur (implémentation non fournie)
        saveUserToken(savedUser, jwtToken);
        // Construction de la réponse d'authentification
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
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
