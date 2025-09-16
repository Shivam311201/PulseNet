package com.pulsenet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.repository.DeviceTelemetryRepository;
import com.pulsenet.api.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class DeviceTelemetryService {

    private final DeviceTelemetryRepository telemetryRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeviceTelemetryService(DeviceTelemetryRepository telemetryRepository, UserRepository userRepository) {
        this.telemetryRepository = telemetryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a device telemetry record for a user
     * 
     * @param telemetry The telemetry data to save
     * @return The saved telemetry object
     */
    @Transactional
    public DeviceTelemetry saveTelemetry(DeviceTelemetry telemetry) 
    {
        // Set current timestamp if not already set
        if (telemetry.getTimestamp() == null) {
            telemetry.setTimestamp(Instant.now());
        }
        
        return telemetryRepository.save(telemetry);
    }

    /**
     * Get all telemetry for a specific user
     * 
     * @param userId The ID of the user
     * @return List of telemetry data for the user
     */
    public List<DeviceTelemetry> getTelemetryForUser(String userName) {
        User userOpt = userRepository.findByName(userName);
        return telemetryRepository.findByUser(userOpt);
    }

    /**
     * Get telemetry for a user within a specified time range
     * 
     * @param userId The ID of the user
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the user within the time range
     */
    public List<DeviceTelemetry> getTelemetryForUserInTimeRange(String userName, Instant startTime, Instant endTime) {
        User userOpt = userRepository.findByName(userName);
        return telemetryRepository.findByUserAndTimestampBetween(userOpt, startTime, endTime);
    }

    /**
     * Get the most recent telemetry data for a user
     * 
     * @param userId The ID of the user
     * @return The most recent telemetry data for the user
     */
    public DeviceTelemetry getLatestTelemetryForDevice(String userName) {
        User userOpt = userRepository.findByName(userName);
        return telemetryRepository.findFirstByUserOrderByTimestampDesc(userOpt);
    }
}