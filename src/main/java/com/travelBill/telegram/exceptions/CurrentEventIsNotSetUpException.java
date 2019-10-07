package com.travelBill.telegram.exceptions;

public class CurrentEventIsNotSetUpException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Current event is not set up for this user";
    }
}
