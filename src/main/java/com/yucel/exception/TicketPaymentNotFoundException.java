package com.yucel.exception;

public class TicketPaymentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public TicketPaymentNotFoundException(String id) {
        super("could not find payment with the id: '" + id + "'.");
    }
}