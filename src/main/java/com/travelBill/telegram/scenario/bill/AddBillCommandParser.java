package com.travelBill.telegram.scenario.bill;

import com.travelBill.api.core.bill.Bill;

public class AddBillCommandParser {

    // Command pattern: /add[@bot] 10 $ for something
    public static Bill parse(String command) {
        Bill bill = new Bill();
        String[] words = command.split(" ");

        bill.setAmount(Double.valueOf(words[1]));
        // TODO: 17.01.19 rework it
        bill.setCurrency(words[2]);
        bill.setPurpose(composePurpose(words));

        return bill;
    }

    private static String composePurpose(String[] words) {
        StringBuilder title = new StringBuilder();

        for (int i = 3; i < words.length; i++) {
            title.append(words[i]);
            title.append(" ");
        }

        return title.toString();
    }
}
