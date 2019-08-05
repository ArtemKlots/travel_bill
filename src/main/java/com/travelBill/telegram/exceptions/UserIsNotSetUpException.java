package com.travelBill.telegram.exceptions;

public class UserIsNotSetUpException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Cannot get user from telegram message";
    }
}
