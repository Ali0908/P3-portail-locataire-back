package com.example.p3portaillocataireback.configuration;

import com.example.p3portaillocataireback.repository.TokenRepository;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private  final List<String> WHITE_LIST_URL = List.of("/api/auth/register",
            "/api/auth/login",
            "/api/rentals",
            "/api/messages",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(isPublicUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                // Si non présent, passe à la suite
//                throw new UnauthorizedRequestException("Missing or invalid Authorization header");
//
//            }
            jwt = authHeader.substring(7);

            try {
                userEmail = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                System.err.println("Invalid JWT: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
//            catch (Exception e) {
//                System.err.println("Invalid JWT: " + e.getMessage());
//                throw new UnauthorizedRequestException("Invalid JWT: " + e.getMessage());
//            }

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

    private  Boolean isPublicUrl(String url) {

        return WHITE_LIST_URL.stream().anyMatch(uri -> pathMatcher .match(uri, url));

    }
}
