package com.example.proyectofinal.Modelo;

public class Token {
    private String token;
    private boolean Servertoken;

    public Token() {
    }

    public Token(String token, boolean servertoken) {
        this.token = token;
        Servertoken = servertoken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isServertoken() {
        return Servertoken;
    }

    public void setServertoken(boolean servertoken) {
        Servertoken = servertoken;
    }
}

