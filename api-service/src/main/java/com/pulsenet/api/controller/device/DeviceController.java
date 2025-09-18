package com.pulsenet.api.controller.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pulsenet.api.model.device.*;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.model.user.*;
import com.pulsenet.api.model.dto.device.DeviceTelemetryDTO;
import com.pulsenet.api.service.DeviceTelemetryService;
import com.pulsenet.api.factory.DeviceTelemetryFactory;
import com.pulsenet.api.repository.DeviceRepository;
import com.pulsenet.api.repository.UserRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final UserRepository userRepository; 
    private final DeviceRepository deviceRepository;
    private final DeviceTelemetryService telemetryService;
    private final DeviceTelemetryFactory deviceTelemetryFactory;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository, DeviceTelemetryService telemetryService, DeviceTelemetryFactory deviceTelemetryFactory, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.telemetryService = telemetryService;
        this.deviceTelemetryFactory = deviceTelemetryFactory;
        this.userRepository = userRepository;
    }

    /**
     * Submit device telemetry data
     * 
     * @param request The telemetry data
     * @return Response with success status
     */
    @PostMapping("/telemetry")
    public ResponseEntity<?> submitTelemetry(@RequestBody DeviceTelemetryRequest request) {
        try {
            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User user = userRepository.findByName(userName);

            // Convert request to entity using factory
            DeviceTelemetry telemetry = deviceTelemetryFactory.createFromRequest(request, user);
            
            // Save the telemetry
            telemetryService.saveTelemetry(telemetry);
            
            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Telemetry data saved successfully");
            response.put("timestamp", Instant.now().toString());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("timestamp", Instant.now().toString());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get the latest telemetry data for the current user
     * 
     * @return The latest telemetry data
     */
    @GetMapping("/telemetry/latest")
    public ResponseEntity<?> getLatestTelemetry() {
        try {
            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Get the latest telemetry
            DeviceTelemetry telemetry = telemetryService.getLatestTelemetryForUser(userName);

            // Convert to DTO
            DeviceTelemetryDTO telemetryDTO = DeviceTelemetryDTO.fromEntity(telemetry);

            if (telemetryDTO == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "No telemetry data found for this user");
                return ResponseEntity.ok(response);
            }
            
            // Return the telemetry data as DTO
            return ResponseEntity.ok(telemetryDTO);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("timestamp", Instant.now().toString());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}