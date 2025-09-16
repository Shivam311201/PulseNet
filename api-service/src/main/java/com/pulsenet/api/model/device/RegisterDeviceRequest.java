package com.pulsenet.api.model.device;

public class RegisterDeviceRequest {
    private String name;
    private String id; // Optional: for future use, e.g., device serial number

    public RegisterDeviceRequest() {}

    public RegisterDeviceRequest(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
