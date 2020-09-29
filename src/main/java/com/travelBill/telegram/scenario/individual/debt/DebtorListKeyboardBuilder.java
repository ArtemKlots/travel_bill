package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.debt.DebtSumDto;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DebtorListKeyboardBuilder {
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

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

        List<DebtSumDto> debtsWithUniqueDebtor = debts.stream()
                .filter(distinctByKey(DebtSumDto::getDebtorId))
                .collect(Collectors.toList());

        debtsWithUniqueDebtor.forEach(debt -> {
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
