package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

    private final String token;

    @JsonCreator
    public JwtResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }
}
