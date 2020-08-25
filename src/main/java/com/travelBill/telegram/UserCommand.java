package com.travelBill.telegram;

public enum UserCommand {

    SHOW_DEBTS("Show debts");

    private final String value;

    UserCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
