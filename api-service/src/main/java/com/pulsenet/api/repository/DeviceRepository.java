package com.pulsenet.api.repository;

import com.pulsenet.api.model.device.Device;
import com.pulsenet.api.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	/**
	 * Find all devices belonging to a specific user
	 * 
	 * @param user The user whose devices to find
	 * @param name The name of the device to find
	 * @return Device belonging to the user with the specified name
	 */
	Device findByUserAndName(User user, String name);
	
	/**
	 * Find all devices belonging to a specific user
	 * 
	 * @param user The user whose devices to find
	 * @return List of devices belonging to the user
	 */
	List<Device> findByUser(User user);
}
