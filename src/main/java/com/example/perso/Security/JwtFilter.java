package com.example.perso.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.perso.Service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userDetailsService;

    // Ce filtre s'exécute à chaque requête pour valider le JWT dans le header
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        // Récupération du token dans le header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // supprime "Bearer "
            String email = jwtService.extractEmail(token);

            // Vérifie qu'il n'y a pas encore d'utilisateur authentifié dans ce contexte
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Si le token est valide, configure l'authentification dans le contexte
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Ajoute l'utilisateur dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                System.out.println("Authorization header: " + authHeader);
                System.out.println("Email extracted from token: " + email);
                System.out.println("UserDetails found: " + userDetails.getUsername());
                System.out.println("Is token valid: " + jwtService.validateToken(token, userDetails));

            }
        }

        // Continue le traitement de la requête
        filterChain.doFilter(request, response);
    }
    
}
