package com.pulsenet.api.service;

import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.user.health.UserHealthSignal;
import com.pulsenet.api.repository.UserHealthSignalRepository;
import com.pulsenet.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class UserHealthSignalService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserHealthSignalService.class);
    
    private final UserHealthSignalRepository healthSignalRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public UserHealthSignalService(
            UserHealthSignalRepository healthSignalRepository,
            UserRepository userRepository) {
        this.healthSignalRepository = healthSignalRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * Save a user health signal
     * 
     * @param healthSignal The health signal to save
     * @return The saved health signal
     */
    @Transactional
    public UserHealthSignal saveHealthSignal(UserHealthSignal healthSignal) {
        // Set current timestamp if not already set
        if (healthSignal.getTimestamp() == null) {
            healthSignal.setTimestamp(Instant.now());
        }
        
        return healthSignalRepository.save(healthSignal);
    }
    
    /**
     * Get all health signals for a specific user
     * 
     * @param userId The ID of the user to find health signals for
     * @return List of health signals for the user
     */
    public List<UserHealthSignal> getHealthSignalsForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.warn("User not found with ID: {}", userId);
            return Collections.emptyList();
        }
        
        return healthSignalRepository.findByUser(user);
    }
    
    /**
     * Get all health signals for a user by email
     * 
     * @param email The email of the user to find health signals for
     * @return List of health signals for the user
     */
    public List<UserHealthSignal> getHealthSignalsForUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email);
            return Collections.emptyList();
        }
        
        return healthSignalRepository.findByUser(user);
    }
    
    /**
     * Get health signals for a user within a specified time range
     * 
     * @param userId The ID of the user to find health signals for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of health signals for the user within the time range
     */
    public List<UserHealthSignal> getHealthSignalsForUserInTimeRange(Long userId, Instant startTime, Instant endTime) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.warn("User not found with ID: {}", userId);
            return Collections.emptyList();
        }
        
        return healthSignalRepository.findByUserAndTimestampBetween(user, startTime, endTime);
    }
    
    /**
     * Get health signals for a user by email within a specified time range
     * 
     * @param email The email of the user to find health signals for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of health signals for the user within the time range
     */
    public List<UserHealthSignal> getHealthSignalsForUserByEmailInTimeRange(String email, Instant startTime, Instant endTime) {
        return healthSignalRepository.findByUserEmailAndTimestampBetween(email, startTime, endTime);
    }
    
    /**
     * Get the most recent health signal for a user
     * 
     * @param userId The ID of the user to find health signals for
     * @return The most recent health signal for the user
     */
    public UserHealthSignal getLatestHealthSignalForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.warn("User not found with ID: {}", userId);
            return null;
        }
        
        return healthSignalRepository.findFirstByUserOrderByTimestampDesc(user);
    }
    
    /**
     * Get the most recent health signal for a user by email
     * 
     * @param email The email of the user to find health signals for
     * @return The most recent health signal for the user
     */
    public UserHealthSignal getLatestHealthSignalForUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email);
            return null;
        }
        
        return healthSignalRepository.findFirstByUserOrderByTimestampDesc(user);
    }
}