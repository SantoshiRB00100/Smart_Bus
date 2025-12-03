package com.smartbus.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String provider;

    // Constructors
    public SignupRequest() {}

    public SignupRequest(String name, String email, String password, String role, String provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
}


