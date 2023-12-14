package com.practice.financialtracker.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {
    private int statusCode;
    private  boolean success;
    private String message;
    private T response;
    public ResponseWrapper() {
    }

    public ResponseWrapper( boolean success, int statusCode, String message, T response) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }
}
