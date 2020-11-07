package com.habeebcycle.marketplace.exception.http;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class HttpErrorInfo {

    private final String path;
    private final HttpStatus httpStatus;
    private final String message;
    private final ZonedDateTime timestamp;

    public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;

        timestamp = ZonedDateTime.now();
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getError(){
        return httpStatus.getReasonPhrase();
    }
}
