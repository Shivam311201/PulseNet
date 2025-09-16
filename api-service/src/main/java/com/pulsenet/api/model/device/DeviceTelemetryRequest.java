package com.pulsenet.api.model.device;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceTelemetryRequest {

    @JsonProperty("device_name")
    private String deviceName;

    @JsonProperty("app_usage")
    private List<AppUsageDTO> appUsage;

    @JsonProperty("screen")
    private ScreenInfoDTO screen;
    
    @JsonProperty("battery")
    private BatteryInfoDTO battery;
    
    @JsonProperty("network")
    private NetworkInfoDTO network;
    
    @JsonProperty("location")
    private LocationInfoDTO location;
    
    @JsonProperty("errors")
    private ErrorsInfoDTO errors;
    
    // Getters and setters
    public List<AppUsageDTO> getAppUsage() {
        return appUsage;
    }
    
    public void setAppUsage(List<AppUsageDTO> appUsage) {
        this.appUsage = appUsage;
    }
    
    public ScreenInfoDTO getScreen() {
        return screen;
    }
    
    public void setScreen(ScreenInfoDTO screen) {
        this.screen = screen;
    }
    
    public BatteryInfoDTO getBattery() {
        return battery;
    }
    
    public void setBattery(BatteryInfoDTO battery) {
        this.battery = battery;
    }
    
    public NetworkInfoDTO getNetwork() {
        return network;
    }
    
    public void setNetwork(NetworkInfoDTO network) {
        this.network = network;
    }
    
    public LocationInfoDTO getLocation() {
        return location;
    }
    
    public void setLocation(LocationInfoDTO location) {
        this.location = location;
    }
    
    public ErrorsInfoDTO getErrors() {
        return errors;
    }
    
    public void setErrors(ErrorsInfoDTO errors) {
        this.errors = errors;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    
    // DTO inner classes for mapping JSON to objects
    public static class AppUsageDTO {
        @JsonProperty("appName")
        private String appName;
        
        @JsonProperty("usage_duration_ms")
        private Long usageDurationMs;
                
        // Getters and setters
        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
        
        public Long getUsageDurationMs() {
            return usageDurationMs;
        }
        
        public void setUsageDurationMs(Long usageDurationMs) {
            this.usageDurationMs = usageDurationMs;
        }
    }
    
    public static class ScreenInfoDTO {
        @JsonProperty("screen_time")
        private Integer screenTime;
        
        @JsonProperty("unlock_count")
        private Integer unlockCount;
        
        // Getters and setters
        public Integer getScreenTime() {
            return screenTime;
        }
        
        public void setScreenTime(Integer screenTime) {
            this.screenTime = screenTime;
        }
        
        public Integer getUnlockCount() {
            return unlockCount;
        }
        
        public void setUnlockCount(Integer unlockCount) {
            this.unlockCount = unlockCount;
        }
    }
    
    public static class BatteryInfoDTO {
        @JsonProperty("level")
        private Integer level;
        
        @JsonProperty("is_charging")
        private Boolean isCharging;
        
        @JsonProperty("charging_type")
        private String chargingType;
        
        @JsonProperty("temperature")
        private Integer temperature;
        
        // Getters and setters
        public Integer getLevel() {
            return level;
        }
        
        public void setLevel(Integer level) {
            this.level = level;
        }
        
        public Boolean getIsCharging() {
            return isCharging;
        }
        
        public void setIsCharging(Boolean isCharging) {
            this.isCharging = isCharging;
        }
        
        public String getChargingType() {
            return chargingType;
        }
        
        public void setChargingType(String chargingType) {
            this.chargingType = chargingType;
        }
        
        public Integer getTemperature() {
            return temperature;
        }
        
        public void setTemperature(Integer temperature) {
            this.temperature = temperature;
        }
    }
    
    public static class NetworkInfoDTO {
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("signal_strength")
        private Integer signalStrength;
        
        @JsonProperty("carrier")
        private String carrier;
        
        @JsonProperty("data_usage")
        private Long dataUsage;
        
        // Getters and setters
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public Integer getSignalStrength() {
            return signalStrength;
        }
        
        public void setSignalStrength(Integer signalStrength) {
            this.signalStrength = signalStrength;
        }
        
        public String getCarrier() {
            return carrier;
        }
        
        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }
        
        public Long getDataUsage() {
            return dataUsage;
        }
        
        public void setDataUsage(Long dataUsage) {
            this.dataUsage = dataUsage;
        }
    }
    
    public static class LocationInfoDTO {
        @JsonProperty("latitude")
        private Double latitude;
        
        @JsonProperty("longitude")
        private Double longitude;
        
        @JsonProperty("altitude")
        private Double altitude;
                
        // Getters and setters
        public Double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
        
        public Double getAltitude() {
            return altitude;
        }
        
        public void setAltitude(Double altitude) {
            this.altitude = altitude;
        }
    }
    
    public static class ErrorsInfoDTO {
        @JsonProperty("crash_count")
        private Integer crashCount;
        
        @JsonProperty("anr_count")
        private Integer anrCount;
                
        // Getters and setters
        public Integer getCrashCount() {
            return crashCount;
        }
        
        public void setCrashCount(Integer crashCount) {
            this.crashCount = crashCount;
        }
        
        public Integer getAnrCount() {
            return anrCount;
        }
        
        public void setAnrCount(Integer anrCount) {
            this.anrCount = anrCount;
        }
    }
}