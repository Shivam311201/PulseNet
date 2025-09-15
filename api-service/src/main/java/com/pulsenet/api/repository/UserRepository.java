package com.pulsenet.api.repository;

import com.pulsenet.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository marks this as a Spring Data repository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA will automatically implement basic CRUD methods
    User findByEmail(String email);
    User findByName(String name);
}
