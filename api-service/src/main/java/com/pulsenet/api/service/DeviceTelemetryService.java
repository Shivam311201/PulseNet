package com.pulsenet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.device.Device;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.repository.DeviceTelemetryRepository;
import com.pulsenet.api.repository.UserRepository;
import com.pulsenet.api.repository.DeviceRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class DeviceTelemetryService {

    private final DeviceTelemetryRepository telemetryRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceTelemetryService(
            DeviceTelemetryRepository telemetryRepository, 
            UserRepository userRepository,
            DeviceRepository deviceRepository) {
        this.telemetryRepository = telemetryRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * Save a device telemetry record
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
     * Get all telemetry for a specific user's devices
     * 
     * @param userName The username to find telemetry for
     * @return List of telemetry data for the user's devices
     */
    public List<DeviceTelemetry> getTelemetryForUser(String userName) {
        User user = userRepository.findByName(userName);
        if (user == null) {
            return Collections.emptyList();
        }

        // Get all devices for user
        List<Device> userDevices = deviceRepository.findByUser(user);
        if (userDevices.isEmpty()) {
            return Collections.emptyList();
        }

        // Find all telemetry for user's devices
        return telemetryRepository.findByDeviceIn(userDevices);
    }

    /**
     * Get telemetry for a user's devices within a specified time range
     * 
     * @param userName The username to find telemetry for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the user's devices within the time range
     */
    public List<DeviceTelemetry> getTelemetryForUserInTimeRange(String userName, Instant startTime, Instant endTime) {
        User user = userRepository.findByName(userName);
        if (user == null) {
            return Collections.emptyList();
        }

        // Get all devices for user
        List<Device> userDevices = deviceRepository.findByUser(user);
        if (userDevices.isEmpty()) {
            return Collections.emptyList();
        }

        // Find all telemetry for user's devices within time range
        return telemetryRepository.findByDeviceInAndTimestampBetween(userDevices, startTime, endTime);
    }

    /**
     * Get the most recent telemetry data from a user's devices
     * 
     * @param userName The username to find telemetry for
     * @return The most recent telemetry data from any of the user's devices
     */
    public DeviceTelemetry getLatestTelemetryForUser(String userName) {
        User user = userRepository.findByName(userName);
        if (user == null) {
            return null;
        }
        
        // Get all devices for user
        List<Device> userDevices = deviceRepository.findByUser(user);
        if (userDevices.isEmpty()) {
            return null;
        }

        // Find latest telemetry for any of user's devices
        return telemetryRepository.findFirstByDeviceInOrderByTimestampDesc(userDevices);
    }

    /**
     * Get all telemetry for a specific device
     * 
     * @param device The device to find telemetry for
     * @return List of telemetry data for the device
     */
    public List<DeviceTelemetry> getTelemetryForDevice(Device device) {
        return telemetryRepository.findByDevice(device);
    }

    /**
     * Get telemetry for a device within a specified time range
     * 
     * @param device The device to find telemetry for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the device within the time range
     */
    public List<DeviceTelemetry> getTelemetryForDeviceInTimeRange(Device device, Instant startTime, Instant endTime) {
        return telemetryRepository.findByDeviceAndTimestampBetween(device, startTime, endTime);
    }

    /**
     * Get the most recent telemetry data for a device
     * 
     * @param device The device to find telemetry for
     * @return The most recent telemetry data for the device
     */
    public DeviceTelemetry getLatestTelemetryForDevice(Device device) {
        return telemetryRepository.findFirstByDeviceOrderByTimestampDesc(device);
    }
}