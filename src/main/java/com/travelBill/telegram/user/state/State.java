package com.travelBill.telegram.user.state;

public enum State {
    START("START"),
    SEND_MONEY("SEND_MONEY");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
