package com.pulsenet.api.service;

import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.auth.*;
import com.pulsenet.api.repository.UserRepository;
import com.pulsenet.api.factory.UserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service marks this as a business logic/service class in Spring Boot
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserFactory userFactory;

    // Handles signup, always as USER
    public LoginResponse signup(SignupRequest signupRequest) {
        LoginResponse response = new LoginResponse();
        
        // Use factory to create a standard user
        User user = userFactory.createStandardUser(
            signupRequest.getUsername(),  // Assuming SignupRequest has name/username field
            signupRequest.getEmail(),
            signupRequest.getPassword()
        );
        
        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Save the user
        userRepository.save(user);
        
        // Prepare response
        response.setStatus(201);
        response.setUsername(user.getUsername());
        response.setToken(token);
        return response;
    }

    // Handles login, returns JWT token if successful
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        
        // Find user in repository
        User user = userRepository.findByName(loginRequest.getUsername());
        
        // Check if user exists and password matches
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) 
        {
            // Generate JWT token
            String token = jwtService.generateToken(user);
            
            // Successful login
            response.setStatus(200);
            response.setToken(token);
            response.setUsername(user.getUsername());
            return response;
        }
        
        // Failed login
        response.setStatus(401);
        response.setToken(null);
        response.setUsername(loginRequest.getUsername());
        return response;
    }

}
