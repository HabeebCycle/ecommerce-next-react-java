package com.habeebcycle.marketplace.payload;

import org.springframework.http.HttpStatus;

public class APIResponse {

    private Boolean success;
    private String message;
    private HttpStatus statusCode;

    public APIResponse(Boolean success, String message, HttpStatus statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
