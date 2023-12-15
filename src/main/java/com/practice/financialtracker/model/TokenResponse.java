package com.practice.financialtracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String token;
    public TokenResponse(String token ) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
