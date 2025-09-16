package com.pulsenet.api.repository;

import com.pulsenet.api.model.user.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	Device findByUserAndName(com.pulsenet.api.model.user.User user, String name);
}
