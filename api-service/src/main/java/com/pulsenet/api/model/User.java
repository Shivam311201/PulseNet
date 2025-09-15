package com.pulsenet.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

// @Entity tells Spring Boot this class should be mapped to a database table
@Entity
@Table(name = "users")
public class User {
    // @Id marks this field as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column maps this field to a column in the table
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    // Devices registered to the user (simple string list for now)
    @ElementCollection
    private List<String> devices;

    // Role: REGULAR or ADMIN
    @Column(nullable = false)
    private String role;

    // Getters and setters omitted for brevity
    // ...

    // Constructors, getters, setters, etc.
    public User() {}

    public User(String name, String email, List<String> devices, String role) {
        this.name = name;
        this.email = email;
        this.devices = devices;
        this.role = role;
    }

    public User(String name, String email, String password, List<String> devices, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.devices = devices;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return name; }
    public void setUsername(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getDevices() { return devices; }
    public void setDevices(List<String> devices) { this.devices = devices; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
