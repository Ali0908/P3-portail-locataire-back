package com.example.p3portaillocataireback.configuration;

import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.repository.TokenRepository;
import com.example.p3portaillocataireback.services.interfaces.JwtAuthenticationFilterInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements JwtAuthenticationFilterInterface {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final List<String> WHITE_LIST_URL = List.of("/api/auth/register",
            "/api/auth/login",
            "/api/rentals",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        logger.info("Processing request to URL: {}", request.getRequestURI());

        if (isPublicUrl(request.getRequestURI())) {
            logger.info("Public URL accessed: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Authorization header is missing or invalid");
                throw new UnauthorizedRequestException("Authorization header is missing or invalid");            }
            jwt = authHeader.substring(7);

            try {
                userEmail = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("Invalid JWT: {}", e.getMessage());
                throw new UnauthorizedRequestException("User not found");
            }

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }


    }

    private Boolean isPublicUrl(String url) {

        return WHITE_LIST_URL.stream().anyMatch(uri -> pathMatcher.match(uri, url));

    }
}
