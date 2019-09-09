package com.travelBill.telegram.scenario.individual.bill;

import com.travelBill.api.core.bill.Bill;

public class AddBillCommandParser {
    private static final int AMOUNT_INDEX = 0;
    private static final int CURRENCY_INDEX = 1;
    private static final int PURPOSE_INDEX = 2;

    // Command pattern: 10 $ for something
    public static Bill parse(String command) {
        Bill bill = new Bill();
        String[] words = command.split(" ");

        bill.setAmount(Double.valueOf(words[AMOUNT_INDEX]));
        // TODO: 17.01.19 rework it
        bill.setCurrency(words[CURRENCY_INDEX]);
        bill.setPurpose(composePurpose(words));

        return bill;
    }

    public static boolean isContribution(String string) {
        if (string == null) {
            return false;
        }

        String firstSymbol = String.valueOf(string.charAt(0));
        int minAvailableStringSequence = 2;

        try {
            Integer.parseInt(firstSymbol);
        } catch (NumberFormatException e) {
            return false;
        }

        return string.split(" ").length > minAvailableStringSequence;

    }

    private static String composePurpose(String[] words) {
        StringBuilder title = new StringBuilder();

        for (int i = PURPOSE_INDEX; i < words.length; i++) {
            title.append(words[i]);
            title.append(" ");
        }

        return title.toString().trim();
    }
}
