package com.example.FDsystem.response;

import com.example.FDsystem.payloadresposne.LoginMesage;

public class AuthResponse {

    private String token;
    private LoginMesage loginMessage;

    public AuthResponse(String token, LoginMesage loginMessage) {
        this.token = token;
        this.loginMessage = loginMessage;
    }

}
