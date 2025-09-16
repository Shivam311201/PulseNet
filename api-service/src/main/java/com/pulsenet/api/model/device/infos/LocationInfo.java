package com.pulsenet.api.model.device.infos;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class LocationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;
    
    @Column
    private Double altitude;
    
    @Column
    private Float accuracy; // in meters
    
    @Column
    private String provider; // GPS, Network, etc.
    
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
    
    public Float getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
}