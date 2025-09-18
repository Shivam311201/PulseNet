package com.pulsenet.api.controller.device;

import com.pulsenet.api.model.device.*;
import com.pulsenet.api.model.user.User;
import com.pulsenet.api.repository.DeviceRepository;
import com.pulsenet.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/device-management")
public class DeviceManagementController {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeviceManagementController(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerDevice(@RequestBody RegisterDeviceRequest request) {
        String deviceName = request.getName();
        if (deviceName == null || deviceName.isEmpty()) {
            return ResponseEntity.badRequest().body("Device name is required");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByName(userName);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        Device existingDevice = deviceRepository.findByUserAndName(user, deviceName);
        if (existingDevice != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Device with this name already exists");
        }

        Device device = new Device(deviceName, user);
        deviceRepository.save(device);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("deviceId", device.getId());
        return ResponseEntity.ok(response);
    }
}
