package com.zest.assignment.exception;

import java.util.List;

public class CustomException extends RuntimeException {
    private int statusCode;
    private List<String> errors;

    public CustomException(String message, int statusCode, List<String> errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}


