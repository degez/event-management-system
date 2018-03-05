package com.yucel.exception;

public class ValidationOfValuesException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public ValidationOfValuesException(String error) {
        super(error);
    }
}