package com.example.proyectofinal.Modelo;

public class Token {
    private String token;
    private boolean isServertoken;

    public Token(String token, boolean isServertoken) {
        this.token = token;
        this.isServertoken = isServertoken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isServertoken() {
        return isServertoken;
    }

    public void setServertoken(boolean servertoken) {
        isServertoken = servertoken;
    }
}