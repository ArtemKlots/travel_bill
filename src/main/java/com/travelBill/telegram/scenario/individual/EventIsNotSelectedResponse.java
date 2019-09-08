package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.Response;

public class EventIsNotSelectedResponse extends Response {
    public EventIsNotSelectedResponse() {
        this.message = "It looks like you have not the current event. Join the existing or create a new one";
    }
}
