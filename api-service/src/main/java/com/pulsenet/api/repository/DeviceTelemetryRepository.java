package com.pulsenet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.model.device.Device;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.Instant;
import java.util.List;

@Repository
public interface DeviceTelemetryRepository extends JpaRepository<DeviceTelemetry, Long> {
    
    /**
     * Find all telemetry data for a specific device
     * 
     * @param device The device to find telemetry for
     * @return List of telemetry data for the device
     */
    List<DeviceTelemetry> findByDevice(Device device);
    
    /**
     * Find all telemetry data for a list of devices
     * 
     * @param devices The list of devices to find telemetry for
     * @return List of telemetry data for the devices
     */
    List<DeviceTelemetry> findByDeviceIn(List<Device> devices);
    
    /**
     * Find all telemetry data for a specific device within a time range
     * 
     * @param device The device to find telemetry for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the device within the time range
     */
    List<DeviceTelemetry> findByDeviceAndTimestampBetween(Device device, Instant startTime, Instant endTime);
    
    /**
     * Find all telemetry data for a list of devices within a time range
     * 
     * @param devices The list of devices to find telemetry for
     * @param startTime The start of the time range
     * @param endTime The end of the time range
     * @return List of telemetry data for the devices within the time range
     */
    List<DeviceTelemetry> findByDeviceInAndTimestampBetween(List<Device> devices, Instant startTime, Instant endTime);
    
    /**
     * Find the most recent telemetry data for a specific device
     * 
     * @param device The device to find telemetry for
     * @return The most recent telemetry data for the device
     */
    DeviceTelemetry findFirstByDeviceOrderByTimestampDesc(Device device);
    
    /**
     * Find the most recent telemetry data from a list of devices
     * 
     * @param devices The list of devices to find telemetry for
     * @return The most recent telemetry data from any of the devices
     */
    DeviceTelemetry findFirstByDeviceInOrderByTimestampDesc(List<Device> devices);
}