package com.pulsenet.api.service;

import com.pulsenet.api.model.dto.health.UserHealthSignalDTO;
import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.user.health.UserHealthSignal;
import com.pulsenet.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserHealthSignalConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(UserHealthSignalConsumerService.class);

    private final UserHealthSignalService healthSignalService;
    private final UserRepository userRepository;

    @Autowired
    public UserHealthSignalConsumerService(
            UserHealthSignalService healthSignalService,
            UserRepository userRepository) {
        this.healthSignalService = healthSignalService;
        this.userRepository = userRepository;
    }

    /**
     * Consumes user health signals from Kafka topic and persists them to the database
     * 
     * @param healthSignalDTO The health signal DTO from Kafka
     */
    @KafkaListener(
            topics = "${kafka.topic.user-health-signals}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "userHealthSignalKafkaListenerContainerFactory")
    public void consumeUserHealthSignal(UserHealthSignalDTO healthSignalDTO) {
        logger.info("Received user health signal: {}", healthSignalDTO);
        
        try {
            // Find the user by ID or email
            User user = null;
            
            if (healthSignalDTO.getUserName() != null) {
                user = userRepository.findByName(healthSignalDTO.getUserName());
            }
                        
            if (user == null) {
                logger.error("User not found with name: {}", healthSignalDTO.getUserName());
                return;
            }
            
            // Create a new UserHealthSignal
            UserHealthSignal healthSignal = new UserHealthSignal();
            healthSignal.setUser(user);
            healthSignal.setTimestamp(healthSignalDTO.getTimestamp() != null ? 
                    healthSignalDTO.getTimestamp() : Instant.now());
            
            // Set health data
            healthSignal.setHeartRate(healthSignalDTO.getHeartRate());
            healthSignal.setSteps(healthSignalDTO.getSteps());
            healthSignal.setCaloriesBurned(healthSignalDTO.getCaloriesBurned());
            healthSignal.setDistanceWalked(healthSignalDTO.getDistanceWalked());
            healthSignal.setSleepState(healthSignalDTO.getSleepState());
            healthSignal.setSleepDurationMinutes(healthSignalDTO.getSleepDurationMinutes());
            healthSignal.setBodyTemperature(healthSignalDTO.getBodyTemperature());
            healthSignal.setBloodOxygen(healthSignalDTO.getBloodOxygen());
            healthSignal.setBloodPressureSystolic(healthSignalDTO.getBloodPressureSystolic());
            healthSignal.setBloodPressureDiastolic(healthSignalDTO.getBloodPressureDiastolic());
            healthSignal.setSourceDeviceName(healthSignalDTO.getSourceDeviceName());
            
            // Save the health signal
            UserHealthSignal savedSignal = healthSignalService.saveHealthSignal(healthSignal);
            logger.info("Saved user health signal with ID: {}", savedSignal.getId());
            
        } catch (Exception e) {
            logger.error("Error processing user health signal: {}", e.getMessage(), e);
        }
    }
}