package com.pulsenet.api.model.device.infos;

import javax.persistence.*;

@Entity
@Table(name = "app_usage")
public class AppUsage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String appName;
    
    @Column(nullable = false)
    private Long usageDurationMs;
        
    @ManyToOne
    @JoinColumn(name = "telemetry_id")
    private DeviceTelemetry telemetry;
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
        
    public DeviceTelemetry getTelemetry() {
        return telemetry;
    }
    
    public void setTelemetry(DeviceTelemetry telemetry) {
        this.telemetry = telemetry;
    }
}