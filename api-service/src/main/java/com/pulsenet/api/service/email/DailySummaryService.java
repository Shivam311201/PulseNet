package com.pulsenet.api.service.email;

import com.pulsenet.api.model.device.Device;
import com.pulsenet.api.model.device.infos.DeviceTelemetry;
import com.pulsenet.api.model.user.User;
import com.pulsenet.api.model.user.health.UserHealthSignal;
import com.pulsenet.api.repository.DeviceRepository;
import com.pulsenet.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.pulsenet.api.service.DeviceTelemetryService;
import com.pulsenet.api.service.UserHealthSignalService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for generating and sending daily summaries of health and device telemetry data
 */
@Service
@EnableScheduling
public class DailySummaryService {

    private static final Logger logger = LoggerFactory.getLogger(DailySummaryService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserHealthSignalService healthSignalService;
    private final DeviceTelemetryService telemetryService;
    private final EmailService emailService;

    @Autowired
    public DailySummaryService(
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            UserHealthSignalService healthSignalService,
            DeviceTelemetryService telemetryService,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.healthSignalService = healthSignalService;
        this.telemetryService = telemetryService;
        this.emailService = emailService;
    }

    /**
     * Generate and send daily summaries to all users
     * Scheduled to run at 6:00 AM every day (configurable via application.properties)
     */
    @Scheduled(cron = "${report.scheduler.cron:0 0 6 * * *}")
    public void generateAndSendDailySummaries() {
        logger.info("Starting daily summary generation for all users");
        
        // Get all users
        List<User> users = userRepository.findAll();
        
        if (users.isEmpty()) {
            logger.warn("No users found in the system");
            return;
        }
        
        // Define the time range for yesterday's data
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(1000, ChronoUnit.DAYS);
        
        logger.info("Generating summaries for time period: {} to {}", startTime, endTime);
        
        // Process each user
        for (User user : users) {
            try {
                logger.info("Generating daily summary for user: {}", user.getUsername());
                
                // Generate the summary
                String emailContent = generateUserDailySummary(user, startTime, endTime);
                
                // Send the email
                String subject = "Your PulseNet Daily Summary - " + 
                        LocalDateTime.ofInstant(endTime, ZoneId.systemDefault()).format(DATE_FORMATTER);
                
                emailService.sendDailySummaryEmail(user.getEmail(), user.getUsername(), subject, emailContent);
                
                logger.info("Daily summary email sent to user: {}", user.getEmail());
            } catch (Exception e) {
                logger.error("Error generating or sending daily summary for user {}: {}", 
                        user.getUsername(), e.getMessage(), e);
            }
        }
        
        logger.info("Completed daily summary generation for all users");
    }

    /**
     * Generate a daily summary for a specific user
     * 
     * @param user The user to generate the summary for
     * @param startTime The start time of the period to include in the summary
     * @param endTime The end time of the period to include in the summary
     * @return A formatted string containing the user's daily summary
     */
    private String generateUserDailySummary(User user, Instant startTime, Instant endTime) {
        // Get user's health signals
        List<UserHealthSignal> healthSignals = healthSignalService.getHealthSignalsForUserInTimeRange(
                user.getUsername(), startTime, endTime);
        
        // Get user's devices
        List<Device> devices = deviceRepository.findByUser(user);
        
        // Get device telemetry for all devices
        List<DeviceTelemetry> deviceTelemetry = new ArrayList<>();
        for (Device device : devices) {
            deviceTelemetry.addAll(telemetryService.getTelemetryForDeviceInTimeRange(
                    device, startTime, endTime));
        }
        
        // Build the email content
        StringBuilder emailContent = new StringBuilder();
        
        // Date of summary
        emailContent.append("Daily Health and Device Summary for ");
        emailContent.append(LocalDateTime.ofInstant(startTime, ZoneId.systemDefault()).format(DATE_FORMATTER));
        emailContent.append(".\n\n");
        
        // Health Summary
        emailContent.append("===== HEALTH SUMMARY =====\n\n");
        
        if (healthSignals.isEmpty()) {
            emailContent.append("No health data recorded for this period.\n\n");
        } else {
            // Calculate averages and totals
            OptionalDouble avgHeartRate = healthSignals.stream()
                    .filter(s -> s.getHeartRate() != null)
                    .mapToInt(UserHealthSignal::getHeartRate)
                    .average();
            
            int totalSteps = healthSignals.stream()
                    .filter(s -> s.getSteps() != null)
                    .mapToInt(UserHealthSignal::getSteps)
                    .sum();
            
            int totalCalories = healthSignals.stream()
                    .filter(s -> s.getCaloriesBurned() != null)
                    .mapToInt(UserHealthSignal::getCaloriesBurned)
                    .sum();
            
            double totalDistance = healthSignals.stream()
                    .filter(s -> s.getDistanceWalked() != null)
                    .mapToDouble(s -> s.getDistanceWalked())
                    .sum();
            
            OptionalDouble avgBloodOxygen = healthSignals.stream()
                    .filter(s -> s.getBloodOxygen() != null)
                    .mapToInt(UserHealthSignal::getBloodOxygen)
                    .average();
            
            OptionalDouble avgSystolic = healthSignals.stream()
                    .filter(s -> s.getBloodPressureSystolic() != null)
                    .mapToInt(UserHealthSignal::getBloodPressureSystolic)
                    .average();
            
            OptionalDouble avgDiastolic = healthSignals.stream()
                    .filter(s -> s.getBloodPressureDiastolic() != null)
                    .mapToInt(UserHealthSignal::getBloodPressureDiastolic)
                    .average();
            
            int totalSleepMinutes = healthSignals.stream()
                    .filter(s -> s.getSleepDurationMinutes() != null)
                    .mapToInt(UserHealthSignal::getSleepDurationMinutes)
                    .sum();
            
            emailContent.append("Average Heart Rate: ").append(avgHeartRate.isPresent() ? 
                    String.format("%.0f", avgHeartRate.getAsDouble()) : "N/A").append(" BPM\n");
            
            emailContent.append("Total Steps: ").append(totalSteps).append("\n");
            
            emailContent.append("Total Calories Burned: ").append(totalCalories).append(" cal\n");
            
            emailContent.append("Total Distance Walked: ").append(String.format("%.2f", totalDistance)).append(" km\n");
            
            emailContent.append("Total Sleep Duration: ").append(totalSleepMinutes / 60).append(" hours, ")
                    .append(totalSleepMinutes % 60).append(" minutes\n");
            
            emailContent.append("Average Blood Oxygen: ").append(avgBloodOxygen.isPresent() ? 
                    String.format("%.0f", avgBloodOxygen.getAsDouble()) : "N/A").append("%\n");
            
            if (avgSystolic.isPresent() && avgDiastolic.isPresent()) {
                emailContent.append("Average Blood Pressure: ")
                        .append(String.format("%.0f", avgSystolic.getAsDouble())).append("/")
                        .append(String.format("%.0f", avgDiastolic.getAsDouble())).append(" mmHg\n");
            } else {
                emailContent.append("Average Blood Pressure: N/A\n");
            }
            
            emailContent.append("\n");
        }
        
        // Device Summary
        emailContent.append("===== DEVICE USAGE SUMMARY =====\n\n");
        
        if (devices.isEmpty()) {
            emailContent.append("No devices registered.\n\n");
        } else {
            emailContent.append("Registered Devices: ").append(devices.size()).append("\n");
            
            for (Device device : devices) {
                emailContent.append("\nDevice: ").append(device.getName()).append("\n");
                
                // Filter telemetry for this device
                List<DeviceTelemetry> deviceData = deviceTelemetry.stream()
                        .filter(t -> t.getDevice().getId().equals(device.getId()))
                        .collect(Collectors.toList());
                
                if (deviceData.isEmpty()) {
                    emailContent.append("  No telemetry data recorded for this period.\n");
                    continue;
                }
                
                // Calculate screen time
                long totalScreenTimeMs = deviceData.stream()
                        .filter(t -> t.getScreen() != null && t.getScreen().getScreenTime() != null)
                        .mapToLong(t -> t.getScreen().getScreenTime())
                        .sum();
                
                // Calculate average battery level
                OptionalDouble avgBattery = deviceData.stream()
                        .filter(t -> t.getBattery() != null && t.getBattery().getLevel() != null)
                        .mapToInt(t -> t.getBattery().getLevel())
                        .average();
                
                // App usage stats
                Map<String, Long> appUsageMap = new HashMap<>();
                deviceData.stream()
                        .filter(t -> t.getAppUsage() != null)
                        .flatMap(t -> t.getAppUsage().stream())
                        .forEach(app -> {
                            String appName = app.getAppName();
                            Long duration = app.getUsageDurationMs();
                            if (appName != null && duration != null) {
                                appUsageMap.put(appName, 
                                        appUsageMap.getOrDefault(appName, 0L) + duration);
                            }
                        });
                
                // Convert screen time to hours and minutes
                long screenHours = totalScreenTimeMs / (1000 * 60 * 60);
                long screenMinutes = (totalScreenTimeMs / (1000 * 60)) % 60;
                
                emailContent.append("  Screen Time: ").append(screenHours).append(" hours, ")
                        .append(screenMinutes).append(" minutes\n");
                
                emailContent.append("  Average Battery Level: ").append(avgBattery.isPresent() ? 
                        String.format("%.0f", avgBattery.getAsDouble()) : "N/A").append("%\n");
                
                // Top 5 most used apps
                if (!appUsageMap.isEmpty()) {
                    emailContent.append("  Top Apps Used:\n");
                    
                    appUsageMap.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .limit(5)
                            .forEach(entry -> {
                                long appHours = entry.getValue() / (1000 * 60 * 60);
                                long appMinutes = (entry.getValue() / (1000 * 60)) % 60;
                                emailContent.append("    - ").append(entry.getKey())
                                        .append(": ").append(appHours).append(" hours, ")
                                        .append(appMinutes).append(" minutes\n");
                            });
                }
            }
        }
        
        // Health Insights
        emailContent.append("\n===== HEALTH INSIGHTS =====\n\n");
        
        if (!healthSignals.isEmpty()) {
            // Compare with previous day (could be enhanced with more data history)
            OptionalDouble avgHeartRate = healthSignals.stream()
                    .filter(s -> s.getHeartRate() != null)
                    .mapToInt(UserHealthSignal::getHeartRate)
                    .average();
            
            int totalSteps = healthSignals.stream()
                    .filter(s -> s.getSteps() != null)
                    .mapToInt(UserHealthSignal::getSteps)
                    .sum();
            
            // Step goals (10,000 is a common goal)
            if (totalSteps >= 10000) {
                emailContent.append("? Great job! You reached your step goal of 10,000 steps.\n");
            } else {
                int percentToGoal = (totalSteps * 100) / 10000;
                emailContent.append("? You reached ").append(percentToGoal)
                        .append("% of your daily step goal (10,000 steps).\n");
            }
            
            // Heart rate insights
            if (avgHeartRate.isPresent()) {
                double rate = avgHeartRate.getAsDouble();
                if (rate < 60) {
                    emailContent.append("?? Your average heart rate was lower than normal. This could indicate good cardiovascular fitness or rest.\n");
                } else if (rate > 100) {
                    emailContent.append("?? Your average heart rate was higher than normal. This could be due to activity, stress, or other factors.\n");
                } else {
                    emailContent.append("?? Your average heart rate was within normal resting range.\n");
                }
            }
            
            // Sleep insights
            int totalSleepMinutes = healthSignals.stream()
                    .filter(s -> s.getSleepDurationMinutes() != null)
                    .mapToInt(UserHealthSignal::getSleepDurationMinutes)
                    .sum();
            
            if (totalSleepMinutes > 0) {
                if (totalSleepMinutes >= 420) { // 7 hours
                    emailContent.append("?? You got adequate sleep. Good job maintaining healthy sleep habits!\n");
                } else {
                    emailContent.append("?? You got less than the recommended 7 hours of sleep. Consider going to bed earlier tonight.\n");
                }
            }
        } else {
            emailContent.append("No health data available to generate insights.\n");
        }
        
        // Email Footer
        emailContent.append("\n===========================\n\n");
        emailContent.append("Thank you for using PulseNet to monitor your health and device usage.\n");
        emailContent.append("For more detailed analytics, please visit the PulseNet dashboard.\n");
        
        return emailContent.toString();
    }

    /**
     * Manually trigger a daily summary for a specific user
     * 
     * @param userId The ID of the user to generate the summary for
     * @return true if the summary was generated and sent, false otherwise
     */
    public boolean triggerDailySummaryForUser(String userName) {
        try {
            User user = userRepository.findByName(userName);
            if (user == null) {
                logger.warn("User not found with name: {}", userName);
                return false;
            }
            
            // Define the time range for yesterday's data
            Instant endTime = Instant.now().truncatedTo(ChronoUnit.DAYS);
            Instant startTime = endTime.minus(1, ChronoUnit.DAYS);
            
            // Generate the summary
            String emailContent = generateUserDailySummary(user, startTime, endTime);
            
            // Send the email
            String subject = "Your PulseNet Daily Summary - " + 
                    LocalDateTime.ofInstant(endTime, ZoneId.systemDefault()).format(DATE_FORMATTER);
            
            emailService.sendDailySummaryEmail(user.getEmail(), user.getUsername(), subject, emailContent);
            
            logger.info("Manually triggered daily summary email sent to user: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            logger.error("Error generating or sending manual daily summary: {}", e.getMessage(), e);
            return false;
        }
    }
}