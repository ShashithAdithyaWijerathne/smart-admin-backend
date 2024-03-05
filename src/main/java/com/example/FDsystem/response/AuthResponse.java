package com.example.FDsystem.response;

import com.example.FDsystem.payloadresposne.LoginMesage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    private String token;
    private LoginMesage loginMessage;
    private String username;
    private int userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss:MM:dd:yyyy")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss:MM:dd:yyyy")
    private Date expirationDate;

    public AuthResponse() {
    }

    public AuthResponse(String token, LoginMesage loginMessage, String username, int userId, Date createdAt, Date expirationDate) {
        this.token = token;
        this.loginMessage = loginMessage;
        this.username = username;
        this.userId = userId;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginMesage getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(LoginMesage loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
