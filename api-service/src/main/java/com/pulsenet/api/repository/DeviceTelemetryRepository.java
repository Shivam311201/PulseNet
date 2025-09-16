package com.pulsenet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.model.user.User;

import java.time.Instant;
import java.util.List;

@Repository
public interface DeviceTelemetryRepository extends JpaRepository<DeviceTelemetry, Long> {
    
    /**
     * Find all telemetry data for a specific user
     * 
     * @param user The user to find telemetry for
     * @return List of telemetry data for the user
     */
    List<DeviceTelemetry> findByUser(User user);
    
    /**
     * Find all telemetry data for a specific user within a time range
     * 
     * @param user The user to find telemetry for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the user within the time range
     */
    List<DeviceTelemetry> findByUserAndTimestampBetween(User user, Instant startTime, Instant endTime);
    
    /**
     * Find the most recent telemetry data for a specific user
     * 
     * @param user The user to find telemetry for
     * @return The most recent telemetry data for the user
     */
    DeviceTelemetry findFirstByUserOrderByTimestampDesc(User user);
}