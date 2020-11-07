package com.habeebcycle.marketplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Status 500
public class ApplicationException extends RuntimeException{

    public ApplicationException(String message){
        super(message);
    }

    public ApplicationException(String message, Throwable cause){
        super(message, cause);
    }

    public ApplicationException(Throwable cause){
        super(cause);
    }
}
