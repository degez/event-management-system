package com.yucel.exception;

public class BinNumberNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public BinNumberNotFoundException(String id) {
        super("could not find bin number: '" + id + "'.");
    }
}