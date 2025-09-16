package com.pulsenet.api.model.device.infos;

import javax.persistence.*;

@Entity
@Table(name = "screen")
public class ScreenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer screenTime; // in minutes
    
    @Column(nullable = false)
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