package com.pulsenet.api.model.dto.device;

import com.pulsenet.api.model.device.Device;

/**
 * DTO for Device entity
 */
public class DeviceDTO {
    private Long id;
    private String name;
    private String userName;  // Only including user's name instead of whole User object

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Static mapper method
    public static DeviceDTO fromEntity(Device device) {
        if (device == null) return null;
        
        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        if (device.getUser() != null) {
            dto.setUserName(device.getUser().getUsername());
        }
        return dto;
    }
}