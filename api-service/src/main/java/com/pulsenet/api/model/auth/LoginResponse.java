package com.pulsenet.api.model.auth;

// DTO for login response
public class LoginResponse {
    private int status;
    private String token;
    private String username;

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
