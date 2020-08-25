package com.travelBill.telegram;

public enum UserCommand {

    EVENT_DEBTS("Event debts");

    private final String value;

    UserCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
