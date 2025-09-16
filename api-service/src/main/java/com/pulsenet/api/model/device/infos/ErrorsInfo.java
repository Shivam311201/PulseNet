package com.pulsenet.api.model.device.infos;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "errors")
public class ErrorsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Integer crashCount;
    
    @Column
    private Integer anrCount; // Application Not Responding count
        
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