package com.pulsenet.api.model.dto.device;

import java.time.Instant;
import java.util.List;
import com.pulsenet.api.model.device.infos.*;

/**
 * DTO for device telemetry data
 */
public class DeviceTelemetryDTO {
    private Instant timestamp;
    private DeviceDTO device;
    private List<AppUsageDTO> appUsage;
    private ScreenInfo screen;
    private BatteryInfo battery;
    private NetworkInfo network;
    private LocationInfo location;
    private ErrorsInfo errors;

    // Getters and Setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public List<AppUsageDTO> getAppUsage() {
        return appUsage;
    }

    public void setAppUsage(List<AppUsageDTO> appUsage) {
        this.appUsage = appUsage;
    }

    public ScreenInfo getScreen() {
        return screen;
    }

    public void setScreen(ScreenInfo screen) {
        this.screen = screen;
    }

    public BatteryInfo getBattery() {
        return battery;
    }

    public void setBattery(BatteryInfo battery) {
        this.battery = battery;
    }

    public NetworkInfo getNetwork() {
        return network;
    }

    public void setNetwork(NetworkInfo network) {
        this.network = network;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public ErrorsInfo getErrors() {
        return errors;
    }

    public void setErrors(ErrorsInfo errors) {
        this.errors = errors;
    }

    // Static mapper method
    public static DeviceTelemetryDTO fromEntity(DeviceTelemetry entity) {
        if (entity == null) return null;

        DeviceTelemetryDTO dto = new DeviceTelemetryDTO();
        dto.setTimestamp(entity.getTimestamp());
        dto.setDevice(DeviceDTO.fromEntity(entity.getDevice()));
        
        // Convert app usage list to DTOs
        if (entity.getAppUsage() != null) {
            List<AppUsageDTO> appUsageDTOs = entity.getAppUsage().stream()
                .map(AppUsageDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList());
            dto.setAppUsage(appUsageDTOs);
        }

        dto.setScreen(entity.getScreen());
        dto.setBattery(entity.getBattery());
        dto.setNetwork(entity.getNetwork());
        dto.setLocation(entity.getLocation());
        dto.setErrors(entity.getErrors());

        return dto;
    }
}