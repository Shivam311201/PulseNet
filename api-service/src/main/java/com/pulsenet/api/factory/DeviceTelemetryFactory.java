package com.pulsenet.api.factory;

import com.pulsenet.api.model.device.*;
import com.pulsenet.api.model.device.infos.*;
import com.pulsenet.api.model.user.*;
import com.pulsenet.api.model.device.Device;
import com.pulsenet.api.repository.DeviceRepository;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating DeviceTelemetry and its sub-objects from request DTOs.
 */
@Component
public class DeviceTelemetryFactory 
{
    private final DeviceRepository deviceRepository;

    public DeviceTelemetryFactory(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceTelemetry createFromRequest(DeviceTelemetryRequest request, User user) {
        DeviceTelemetry telemetry = new DeviceTelemetry();
        telemetry.setTimestamp(Instant.now());

        // App usage
        if (request.getAppUsage() != null) {
            List<AppUsage> appUsageList = new ArrayList<>();
            for (DeviceTelemetryRequest.AppUsageDTO appUsageDTO : request.getAppUsage()) {
                AppUsage appUsage = new AppUsage();
                appUsage.setAppName(appUsageDTO.getAppName());
                appUsage.setUsageDurationMs(appUsageDTO.getUsageDurationMs());
                appUsageList.add(appUsage);
            }
            telemetry.setAppUsage(appUsageList);
        }

        // Screen info
        if (request.getScreen() != null) {
            ScreenInfo screenInfo = new ScreenInfo();
            screenInfo.setScreenTime(request.getScreen().getScreenTime());
            screenInfo.setUnlockCount(request.getScreen().getUnlockCount());
            telemetry.setScreen(screenInfo);
        }

        // Battery info
        if (request.getBattery() != null) {
            BatteryInfo batteryInfo = new BatteryInfo();
            batteryInfo.setLevel(request.getBattery().getLevel());
            batteryInfo.setIsCharging(request.getBattery().getIsCharging());
            batteryInfo.setChargingType(request.getBattery().getChargingType());
            batteryInfo.setTemperature(request.getBattery().getTemperature());
            telemetry.setBattery(batteryInfo);
        }

        // Network info
        if (request.getNetwork() != null) {
            NetworkInfo networkInfo = new NetworkInfo();
            networkInfo.setType(request.getNetwork().getType());
            networkInfo.setSignalStrength(request.getNetwork().getSignalStrength());
            networkInfo.setCarrier(request.getNetwork().getCarrier());
            networkInfo.setDataUsage(request.getNetwork().getDataUsage());
            telemetry.setNetwork(networkInfo);
        }

        // Location info
        if (request.getLocation() != null) {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setLatitude(request.getLocation().getLatitude());
            locationInfo.setLongitude(request.getLocation().getLongitude());
            locationInfo.setAltitude(request.getLocation().getAltitude());
            telemetry.setLocation(locationInfo);
        }

        // Errors info
        if (request.getErrors() != null) {
            ErrorsInfo errorsInfo = new ErrorsInfo();
            errorsInfo.setCrashCount(request.getErrors().getCrashCount());
            errorsInfo.setAnrCount(request.getErrors().getAnrCount());
            telemetry.setErrors(errorsInfo);
        }

        // Device association (if device name is provided)
        if (request.getDeviceName() != null) {
            Device device = deviceRepository.findByUserAndName(user, request.getDeviceName());
            if (device == null) 
                throw new IllegalArgumentException("Device with name " + request.getDeviceName() + " not found for user " + user.getUsername());
            telemetry.setDevice(device);
        }

        return telemetry;
    }
}
