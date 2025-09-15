package com.pulsenet.api.model.RequestModel;

// DTO for login requests
public class LoginRequest 
{
    private String username;
    private String password;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
