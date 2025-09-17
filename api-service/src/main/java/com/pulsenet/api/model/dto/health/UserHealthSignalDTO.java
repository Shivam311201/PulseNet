package com.pulsenet.api.model.dto.health;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserHealthSignalDTO {
    
    @JsonProperty("user_name")
    private String userName;
        
    @JsonProperty("timestamp")
    private Instant timestamp;
    
    @JsonProperty("heart_rate")
    private Integer heartRate;
    
    @JsonProperty("steps")
    private Integer steps;
    
    @JsonProperty("calories_burned")
    private Integer caloriesBurned;
    
    @JsonProperty("distance_walked")
    private Float distanceWalked;
    
    @JsonProperty("sleep_state")
    private String sleepState;
    
    @JsonProperty("sleep_duration_minutes")
    private Integer sleepDurationMinutes;
    
    @JsonProperty("body_temperature")
    private Float bodyTemperature;
    
    @JsonProperty("blood_oxygen")
    private Integer bloodOxygen;
    
    @JsonProperty("blood_pressure_systolic")
    private Integer bloodPressureSystolic;
    
    @JsonProperty("blood_pressure_diastolic")
    private Integer bloodPressureDiastolic;
    
    @JsonProperty("source_device_name")
    private String sourceDeviceName;

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Float getDistanceWalked() {
        return distanceWalked;
    }

    public void setDistanceWalked(Float distanceWalked) {
        this.distanceWalked = distanceWalked;
    }

    public String getSleepState() {
        return sleepState;
    }

    public void setSleepState(String sleepState) {
        this.sleepState = sleepState;
    }

    public Integer getSleepDurationMinutes() {
        return sleepDurationMinutes;
    }

    public void setSleepDurationMinutes(Integer sleepDurationMinutes) {
        this.sleepDurationMinutes = sleepDurationMinutes;
    }

    public Float getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(Float bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public Integer getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(Integer bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public Integer getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Integer bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Integer getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Integer bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }
    
    public String getSourceDeviceName() {
        return sourceDeviceName;
    }
    
    public void setSourceDeviceName(String sourceDeviceName) {
        this.sourceDeviceName = sourceDeviceName;
    }

    @Override
    public String toString() {
        return "UserHealthSignalDTO{" +
                "userName='" + userName + '\'' +
                ", timestamp=" + timestamp +
                ", heartRate=" + heartRate +
                ", steps=" + steps +
                '}';
    }
}