package com.example.p3portaillocataireback.auth;

import com.example.p3portaillocataireback.configuration.JwtService;
import com.example.p3portaillocataireback.token.Token;
import com.example.p3portaillocataireback.token.TokenRepository;
import com.example.p3portaillocataireback.token.TokenType;
import com.example.p3portaillocataireback.user.Role;
import com.example.p3portaillocataireback.user.UserRepository;
import com.example.p3portaillocataireback.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Création d'un utilisateur
        var user = User.builder()
                //  Conversion du nom d'utilisateur en minuscules
                .name(request.getName())
                // Conversion de l'email en minuscules
                .email(request.getEmail())
                // Encodage du mot de passe
                .password(passwordEncoder.encode(request.getPassword()))
                // Rôle par défaut
                .role((Role.USER))
                .build();
        // Enregistrement de l'utilisateur en base de données
        var savedUser = repository.save(user);
        // Génération du jeton d'accès
        var jwtToken = jwtService.generateToken(user);
        // Sauvegarde du jeton d'accès pour l'utilisateur (implémentation non fournie)
        saveUserToken(savedUser, jwtToken);
        // Construction de la réponse d'authentification
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }


    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
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
