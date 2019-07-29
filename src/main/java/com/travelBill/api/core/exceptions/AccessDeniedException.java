package com.travelBill.api.core.exceptions;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
