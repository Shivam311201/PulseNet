package com.pulsenet.api.model.user.health;

import com.pulsenet.api.model.user.User;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_health_signals")
public class UserHealthSignal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column
    private Integer heartRate; // Beats per minute
    
    @Column
    private Integer steps; // Daily step count
    
    @Column
    private Integer caloriesBurned; // Calories burned in the day
    
    @Column
    private Float distanceWalked; // Distance walked in kilometers
    
    @Column
    private String sleepState; // AWAKE, LIGHT_SLEEP, DEEP_SLEEP, REM_SLEEP
    
    @Column
    private Integer sleepDurationMinutes; // Total sleep duration in minutes
    
    @Column
    private Float bodyTemperature; // Body temperature in Celsius
    
    @Column
    private Integer bloodOxygen; // Blood oxygen level (SpO2) in percentage (1-100)
    
    @Column
    private Integer bloodPressureSystolic; // Systolic blood pressure in mmHg
    
    @Column
    private Integer bloodPressureDiastolic; // Diastolic blood pressure in mmHg
    
    @Column
    private String sourceDeviceName; // Name of the device that sent the signal

    // Constructors
    public UserHealthSignal() {}
    
    public UserHealthSignal(User user, Instant timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}