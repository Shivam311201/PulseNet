package com.pulsenet.api.model.dto.device;

import com.pulsenet.api.model.device.infos.AppUsage;

/**
 * DTO for app usage information
 */
public class AppUsageDTO {
    private String appName;
    private Long usageDurationMs;

    // Getters and Setters
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

    // Static mapper method
    public static AppUsageDTO fromEntity(AppUsage entity) {
        if (entity == null) return null;

        AppUsageDTO dto = new AppUsageDTO();
        dto.setAppName(entity.getAppName());
        dto.setUsageDurationMs(entity.getUsageDurationMs());
        return dto;
    }
}