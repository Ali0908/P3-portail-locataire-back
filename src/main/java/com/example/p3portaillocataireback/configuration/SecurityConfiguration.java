package com.example.p3portaillocataireback.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    // Liste des URL autorisées sans authentification
    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/api/v1/blog",
            "/api/v1/category",
            "api/v1/article",
            "/api/v1/comment",
            "/api/v1/user",
            "/api/v1/blog",
            "/api/v1/token",
            "/api/v1/article/blog/{blog-id}",
            "/api/v1/comment/article/{article-id}",
    };

    @Bean // Crée un bean Spring pour la configuration de la sécurité
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactive la protection CSRF (non applicable aux API stateless)
                .csrf(AbstractHttpConfigurer::disable)
                // Configure l'autorisation des requêtes HTTP

                .authorizeHttpRequests(req ->
                        // Autorise l'accès aux URL de la liste blanche
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                // Requiert l'authentification pour les autres URL en fonction des rôles
                                .requestMatchers("/api/v1/blog/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/api/v1/category/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/api/v1/article/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/api/v1/comment/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/api/v1/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                // Toute autre requete nécessite l'authentification
                                .anyRequest()
                                .authenticated()
                )
                // Désactive la gestion des sessions (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // Définit le fournisseur d'authentification
                .authenticationProvider(authenticationProvider)
                // Ajoute le filtre d'authentification JWT avant la chaîne de filtrage par défaut
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure la déconnexion
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}
