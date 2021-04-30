package com.travelBill.splitBill.core;

import com.travelBill.TravelBillException;

public class AccessDeniedException extends TravelBillException {
    public AccessDeniedException() {
        super("Access denied");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
