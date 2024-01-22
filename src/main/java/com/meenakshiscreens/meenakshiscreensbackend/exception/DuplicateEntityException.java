package com.meenakshiscreens.meenakshiscreensbackend.exception;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
