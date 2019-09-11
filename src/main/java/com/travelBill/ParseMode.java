package com.travelBill;

public enum ParseMode {
    MARKDOWN("MARKDOWN"),
    HTML("HTML"),
    PLAIN_TEXT(null);

    private final String value;

    ParseMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
