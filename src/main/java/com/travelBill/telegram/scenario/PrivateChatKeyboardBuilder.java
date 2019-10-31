package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import org.springframework.stereotype.Service;

@Service
public class PrivateChatKeyboardBuilder {

    public ReplyKeyboard build() {
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton currentEventButton = new ReplyKeyboardButton().setText("Show current event");
        ReplyKeyboardButton switchEventButton = new ReplyKeyboardButton().setText("Switch event");
        ReplyKeyboardButton showLastBillsButton = new ReplyKeyboardButton().setText("Show last bills");
        ReplyKeyboardButton showDebtsButton = new ReplyKeyboardButton().setText("Show debts");
        ReplyKeyboardButton deleteBillButton = new ReplyKeyboardButton().setText("Delete bill");
        ReplyKeyboardButton closeEventButton = new ReplyKeyboardButton().setText("Close event");

        keyboard.addRow(currentEventButton, switchEventButton);
        keyboard.addRow(deleteBillButton, showDebtsButton, showLastBillsButton);
        keyboard.addRow(closeEventButton);

        return keyboard;
    }
}
