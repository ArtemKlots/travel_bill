package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;

import java.util.List;

public class DebtorListKeyboardBuilder {

    public InlineKeyboard build(List<User> users) {
        InlineKeyboard keyboard = new InlineKeyboard();

        users.forEach(user -> {
            String text = user.getFullName();
            keyboard.addRow(new InlineKeyboardButton().setText(text).setCallbackData("send_money_to_" + user.getId()));
        });

        keyboard.addRow(new InlineKeyboardButton().setText("Cancel").setCallbackData("delete_previous_message"));

        return keyboard;
    }

}
