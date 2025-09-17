package com.timesheet.pro.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthResponse {
    // @JsonProperty("token")
    // private String token;
    // public AuthResponse(String token) { this.token = token; }
    private String accessToken;
    private String refreshToken;

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        
    }
}