package com.pulsenet.api.controller;

import com.pulsenet.api.model.dto.health.UserHealthSignalDTO;
import com.pulsenet.api.service.Kafka.HealthSignalProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/health-signals")
public class HealthSignalController {

    private static final Logger logger = LoggerFactory.getLogger(HealthSignalController.class);
    
    private final HealthSignalProducerService producerService;
    
    @Autowired
    public HealthSignalController(HealthSignalProducerService producerService) {
        this.producerService = producerService;
    }
    
    /**
     * Send a health signal to Kafka
     * 
     * @param healthSignal The health signal data to send
     * @return ResponseEntity with status and message
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> sendHealthSignal(@RequestBody UserHealthSignalDTO healthSignal) {
        logger.info("Received request to send health signal: {}", healthSignal);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate required fields
            if (healthSignal.getUserName() == null) {
                response.put("status", "error");
                response.put("message", "user_name must be provided");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Send the health signal
            producerService.sendHealthSignal(healthSignal).get(); // Using .get() to make it synchronous
            
            // Prepare success response
            response.put("status", "success");
            response.put("message", "Health signal sent successfully");
            
            return ResponseEntity.ok(response);
        }
        catch (InterruptedException | ExecutionException e) 
        {
            logger.error("Error sending health signal: {}", e.getMessage(), e);
            
            // Prepare error response
            response.put("status", "error");
            response.put("message", "Failed to send health signal: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Send a test health signal with predefined values
     * 
     * @param userEmail The email of the user (optional)
     * @return ResponseEntity with status and message
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> sendTestHealthSignal(
            @RequestParam(defaultValue = "test@example.com") String userEmail) {
        logger.info("Received request to send test health signal for user: {}", userEmail);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Send the test health signal
            producerService.sendTestHealthSignal(userEmail).get(); // Using .get() to make it synchronous
            
            // Prepare success response
            response.put("status", "success");
            response.put("message", "Test health signal sent successfully for user: " + userEmail);
            
            return ResponseEntity.ok(response);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error sending test health signal: {}", e.getMessage(), e);
            
            // Prepare error response
            response.put("status", "error");
            response.put("message", "Failed to send test health signal: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Send the health signal from test.json file
     * 
     * @return ResponseEntity with status and message
     */
    @PostMapping("/test-json")
    public ResponseEntity<Map<String, Object>> sendTestJsonHealthSignal() {
        logger.info("Received request to send test.json health signal");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Create a health signal based on test.json values
            UserHealthSignalDTO testSignal = new UserHealthSignalDTO();
            testSignal.setUserName("test");
            testSignal.setHeartRate(75);
            testSignal.setSteps(8500);
            testSignal.setCaloriesBurned(350);
            testSignal.setDistanceWalked(6.2f);
            testSignal.setSleepState("LIGHT_SLEEP");
            testSignal.setSleepDurationMinutes(420);
            testSignal.setBodyTemperature(36.7f);
            testSignal.setBloodOxygen(98);
            testSignal.setBloodPressureSystolic(120);
            testSignal.setBloodPressureDiastolic(80);
            testSignal.setSourceDeviceName("shivam-onePlus");
            
            // Send the health signal
            producerService.sendHealthSignal(testSignal).get(); // Using .get() to make it synchronous
            
            // Prepare success response
            response.put("status", "success");
            response.put("message", "Test JSON health signal sent successfully");
            response.put("data", testSignal);
            
            return ResponseEntity.ok(response);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error sending test JSON health signal: {}", e.getMessage(), e);
            
            // Prepare error response
            response.put("status", "error");
            response.put("message", "Failed to send test JSON health signal: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}