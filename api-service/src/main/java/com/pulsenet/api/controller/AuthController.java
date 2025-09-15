package com.pulsenet.api.controller;

import com.pulsenet.api.model.*;
import com.pulsenet.api.model.RequestModel.*;
import com.pulsenet.api.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// @RestController tells Spring Boot this class handles HTTP requests
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
    @Autowired
    private AuthService authService;

    // Signup endpoint
    // Accepts JSON body for signup, encodes password before storing
    @PostMapping("/signup")
    public LoginResponse signup(@RequestBody SignupRequest signupRequest) {
        // Delegate all logic to service
        return authService.signup(signupRequest);
    }

    // Login endpoint using LoginRequest DTO for input
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // Delegate all logic to service
        return authService.login(loginRequest);
    }
}
