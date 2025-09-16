package com.pulsenet.api.model.device.infos;
import com.pulsenet.api.model.user.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "device_telemetry")
public class DeviceTelemetry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Instant timestamp;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @OneToMany(mappedBy="telemetry", cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<AppUsage> appUsage;
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "screen_id")
    private ScreenInfo screen;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "battery_id")
    private BatteryInfo battery;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "network_id")
    private NetworkInfo network;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private LocationInfo location;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "errors_id")
    private ErrorsInfo errors;
    
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
        
    public List<AppUsage> getAppUsage() {
        return appUsage;
    }
    
    public void setAppUsage(List<AppUsage> appUsage) {
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
}