package com.technohacks.bank;

public class ErrorResponse {
    private String errorCode;
    private String message;
    private String details;
    private int status;

    public ErrorResponse(String errorCode, String message, String details, int status) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("✗ ERROR [%d - %s]: %s\n↳ %s",
            status, errorCode, message, details);
    }
}
