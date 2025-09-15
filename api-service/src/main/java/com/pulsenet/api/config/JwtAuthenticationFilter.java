package com.pulsenet.api.config;

import com.pulsenet.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip filter for login/signup endpoints
        String path = request.getRequestURI();
        if (path.contains("/api/auth/login") || path.contains("/api/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get Authorization header
        String authHeader = request.getHeader("Authorization");
        
        // Check if JWT token exists
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7); // Remove "Bearer " prefix
            try {
                // Validate token
                String email = jwtService.validateToken(jwt);
                if (email != null) {
                    // Get role claim from token
                    String role = jwtService.getRoleFromToken(jwt);
                    
                    // Create authentication object
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            email, null,
                            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                    
                    // Set authentication in Spring Security context
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                // Token validation failed
                SecurityContextHolder.clearContext();
            }
        }
        
        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}