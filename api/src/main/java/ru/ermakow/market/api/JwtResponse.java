package ru.ermakow.market.api;

public class JwtResponse {

    private String token;

    public JwtResponse() {}

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

//DTOшка которую мы получаем от AuthController'a
