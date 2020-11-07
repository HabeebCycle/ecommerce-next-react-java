package com.habeebcycle.marketplace.exception.http;

import com.habeebcycle.marketplace.exception.ApplicationException;
import com.habeebcycle.marketplace.exception.BadRequestException;
import com.habeebcycle.marketplace.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(HttpServletRequest request, Exception ex){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody HttpErrorInfo handleBadRequestExceptions(HttpServletRequest request, Exception ex){
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public @ResponseBody HttpErrorInfo handleInternalServerExceptions(HttpServletRequest request, Exception ex){
        return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, Exception ex) {
        final String path = request.getServletPath();
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
