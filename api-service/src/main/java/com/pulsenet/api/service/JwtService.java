package com.pulsenet.api.service;

import com.pulsenet.api.model.user.User;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    // Secret key for signing JWTs (use a secure value in production)
    private final String SECRET_KEY = "your_secret_key";
    private final long EXPIRATION_TIME = 86400000; // 1 day in ms
    
    // Generates a JWT token for a user
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("role", user.getRole())
            .claim("email", user.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    // Validates and parses a JWT token
    public String validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    // Extract role from JWT token
    public String getRoleFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }
    
    // Extract email from JWT token
    public String getEmailFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("email");
    }
}
