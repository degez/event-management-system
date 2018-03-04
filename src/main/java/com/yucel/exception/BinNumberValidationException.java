package com.yucel.exception;

import org.springframework.validation.Errors;

public class BinNumberValidationException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    private Errors            errors;

    public BinNumberValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}