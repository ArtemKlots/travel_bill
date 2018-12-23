package com.travelBill.telegram.exceptions;

public class UserIsNotSetUpException extends TravelBillException {

    @Override
    public String getMessage() {
        return "Cannot get user from telegram message";
    }
}
