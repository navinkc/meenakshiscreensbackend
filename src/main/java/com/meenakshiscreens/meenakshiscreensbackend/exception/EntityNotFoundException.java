package com.meenakshiscreens.meenakshiscreensbackend.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + "with id '" + id + "' does not exists.");
    }

    public EntityNotFoundException(String s, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + "with name or email '" + s + "' does not exists.");
    }
}
