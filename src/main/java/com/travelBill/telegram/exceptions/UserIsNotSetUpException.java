package com.travelBill.telegram.exceptions;

import com.travelBill.api.core.exceptions.TravelBillException;

public class UserIsNotSetUpException extends TravelBillException {

    @Override
    public String getMessage() {
        return "Cannot get user from telegram message";
    }
}
