package com.pulsenet.api.service;

import com.pulsenet.api.model.dto.health.UserHealthSignalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Instant;

@Service
public class HealthSignalProducerService {

    private static final Logger logger = LoggerFactory.getLogger(HealthSignalProducerService.class);

    private final KafkaTemplate<String, UserHealthSignalDTO> kafkaTemplate;
    
    @Value("${kafka.topic.user-health-signals}")
    private String topicName;

    @Autowired
    public HealthSignalProducerService(KafkaTemplate<String, UserHealthSignalDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send a health signal to Kafka
     * 
     * @param healthSignal The health signal to send
     * @return The ListenableFuture for the send operation
     */
    public ListenableFuture<SendResult<String, UserHealthSignalDTO>> sendHealthSignal(UserHealthSignalDTO healthSignal) {
        // Set current timestamp if not already set
        if (healthSignal.getTimestamp() == null) {
            healthSignal.setTimestamp(Instant.now());
        }
        
        logger.info("Sending health signal to topic {}: {}", topicName, healthSignal);

        // The key will be the user name (useful for partitioning)
        String key = healthSignal.getUserName();

        ListenableFuture<SendResult<String, UserHealthSignalDTO>> future = 
                kafkaTemplate.send(topicName, key, healthSignal);
        
        // Add callback for logging success/failure
        future.addCallback(new ListenableFutureCallback<SendResult<String, UserHealthSignalDTO>>() {
            @Override
            public void onSuccess(SendResult<String, UserHealthSignalDTO> result) {
                logger.info("Health signal sent successfully to topic {} with offset {}", 
                        topicName, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failed to send health signal to topic {}: {}", 
                        topicName, ex.getMessage(), ex);
            }
        });
        
        return future;
    }
    
    /**
     * Send a test health signal with predefined values
     * 
     * @param userEmail The email of the user
     * @return The ListenableFuture for the send operation
     */
    public ListenableFuture<SendResult<String, UserHealthSignalDTO>> sendTestHealthSignal(String userName) {
        UserHealthSignalDTO testSignal = new UserHealthSignalDTO();
        testSignal.setUserName(userName);
        testSignal.setTimestamp(Instant.now());
        testSignal.setHeartRate(75);
        testSignal.setSteps(5000);
        testSignal.setCaloriesBurned(250);
        testSignal.setDistanceWalked(3.5f);
        testSignal.setSleepState("AWAKE");
        testSignal.setBloodOxygen(98);
        testSignal.setBloodPressureSystolic(120);
        testSignal.setBloodPressureDiastolic(80);
        testSignal.setBodyTemperature(36.6f);
        testSignal.setSourceDeviceName("API-Test-Device");
        
        return sendHealthSignal(testSignal);
    }
}