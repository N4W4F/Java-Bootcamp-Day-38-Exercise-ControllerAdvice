package com.example.smartmetro.ApiResponse;

public class ApiException extends RuntimeException {
    public ApiException (String message) {
        super(message);
    }
}
