package com.meenakshiscreens.meenakshiscreensbackend.exception;

public class RequestValidationException extends RuntimeException {

    public RequestValidationException() {
    }

    public RequestValidationException(String message) {
        super(message);
    }
}
