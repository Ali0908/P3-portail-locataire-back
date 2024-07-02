package com.example.p3portaillocataireback.configuration;
import com.example.p3portaillocataireback.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1.Extraction du JWT
        // Récupération du JWT
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // Vérification la présence d'un JWT
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si non présent, passe à la suite
            filterChain.doFilter(request, response);
            return;
        }
        // Extraction du JWT
        jwt = authHeader.substring(7);
        // Extraction du username du JWT
        userEmail = (String) jwtService.extractUsername(jwt);
        // 2. Validation du JWT
        // Si username et pas d'authentification
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Charge les infos de l'utilisateur
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Vérification de la validité du token
            var isTokenValid = tokenRepository.findByToken(jwt)
                    // Non expiré et non révoqué
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    // Si token non trouvé, il retourne false
                    .orElse(false);
            // Si token valide et non révoqué
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // Création d'un token d'authentification
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Ajoute des détails d'authentification web
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Place le token dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 3. Poursuite de la requête
        // Passe à la suite
        filterChain.doFilter(request, response);
    }
}
