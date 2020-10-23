package com.thoughtworks.springbootemployee.advice;

public class ErrorResponse {
    private final String message;
    private final String Status;

    public ErrorResponse(String message, String status) {
        this.message = message;
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return Status;
    }
}
