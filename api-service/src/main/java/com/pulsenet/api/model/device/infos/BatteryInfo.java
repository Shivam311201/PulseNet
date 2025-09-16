package com.pulsenet.api.model.device.infos;

import javax.persistence.*;

@Entity
@Table(name = "battery")
public class BatteryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer level; // battery percentage (0-100)
    
    @Column(nullable = false)
    private Boolean isCharging;
    
    @Column
    private String chargingType; // AC, USB, Wireless, etc.
    
    @Column
    private Integer temperature; // in celsius
    
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