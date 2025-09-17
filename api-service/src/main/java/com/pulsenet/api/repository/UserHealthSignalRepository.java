package com.pulsenet.api.repository;

import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.user.health.UserHealthSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UserHealthSignalRepository extends JpaRepository<UserHealthSignal, Long> {
    
    /**
     * Find all health signals for a specific user
     * 
     * @param user The user to find health signals for
     * @return List of health signals for the user
     */
    List<UserHealthSignal> findByUser(User user);
    
    /**
     * Find all health signals for a specific user within a time range
     * 
     * @param user The user to find health signals for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of health signals for the user within the time range
     */
    List<UserHealthSignal> findByUserAndTimestampBetween(User user, Instant startTime, Instant endTime);
    
    /**
     * Find the most recent health signal for a specific user
     * 
     * @param user The user to find health signals for
     * @return The most recent health signal for the user
     */
    UserHealthSignal findFirstByUserOrderByTimestampDesc(User user);
    
    /**
     * Find health signals by user's email
     * 
     * @param email The email of the user to find health signals for
     * @return List of health signals for the user with the specified email
     */
    List<UserHealthSignal> findByUserEmail(String email);
    
    /**
     * Find health signals by user's email within a time range
     * 
     * @param email The email of the user to find health signals for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of health signals for the user with the specified email within the time range
     */
    List<UserHealthSignal> findByUserEmailAndTimestampBetween(String email, Instant startTime, Instant endTime);
}