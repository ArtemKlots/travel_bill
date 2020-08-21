package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.debt.DebtSumDto;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;

import java.util.List;

public class DebtorListKeyboardBuilder {

    public InlineKeyboard buildByUsers(List<User> users) {
        InlineKeyboard keyboard = new InlineKeyboard();

        users.forEach(user -> {
            String text = user.getFullName();
            keyboard.addRow(new InlineKeyboardButton().setText(text).setCallbackData("send_money_to_" + user.getId()));
        });

        keyboard.addRow(new InlineKeyboardButton().setText("Cancel").setCallbackData("delete_previous_message"));

        return keyboard;
    }

    public InlineKeyboard buildByDebts(List<DebtSumDto> debts) {
        InlineKeyboard keyboard = new InlineKeyboard();

        debts.forEach(debt -> {
            String text = "";
            if (debt.getUserFirstName() != null) {
                text += debt.getUserFirstName();
            }

            if (debt.getUserFirstName() != null && debt.getUserLastName() != null) {
                text += " ";
            }

            if (debt.getUserLastName() != null) {
                text += debt.getUserLastName();
            }

            keyboard.addRow(new InlineKeyboardButton().setText(text).setCallbackData("send_money_to_" + debt.getDebtorId()));
        });

        return keyboard;
    }

}
