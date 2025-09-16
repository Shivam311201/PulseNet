package com.pulsenet.api.factory;

import com.pulsenet.api.model.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating User objects with different configurations.
 * This follows the Factory design pattern to centralize user creation logic.
 */
@Component
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a standard user with encoded password
     *
     * @param name        user's name
     * @param email       user's email
     * @param password    raw password (will be encoded)
     * @return configured User object
     */
    public User createStandardUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setDevices(new ArrayList<>());
        return user;
    }
}