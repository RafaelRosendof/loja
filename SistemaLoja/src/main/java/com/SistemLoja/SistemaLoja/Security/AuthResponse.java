package com.SistemLoja.SistemaLoja.Security;

public class AuthResponse {
    private String token;

    // Default constructor
    public AuthResponse() {
    }

    // Constructor with parameter
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
