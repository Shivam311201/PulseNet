package com.pulsenet.api.model.device.infos;

import javax.persistence.*;

@Entity
@Table(name = "network")
public class NetworkInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String type; // WiFi, Mobile, None
    
    @Column
    private Integer signalStrength; // dBm for mobile or quality for WiFi
    
    @Column
    private String carrier; // Mobile carrier name if applicable
    
    @Column
    private Long dataUsage; // in bytes
    
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