package com.travelBill.telegram.driver.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class KeyboardMarkup {
    public List<List<Button>> rows = new ArrayList<>();

    public void addRow(Button... buttons) {
        rows.add(Arrays.asList(buttons));
    }
}
