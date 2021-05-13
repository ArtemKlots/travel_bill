package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import org.springframework.stereotype.Service;

import static com.travelBill.Icons.BLACK_HEART;
import static com.travelBill.telegram.UserCommand.*;

@Service
public class PrivateChatKeyboardBuilder {

    public ReplyKeyboard build() {
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton sendMoneyButton = new ReplyKeyboardButton().setText(ONE_TO_ONE_MENU);

        ReplyKeyboardButton currentEventButton = new ReplyKeyboardButton().setText(CURRENT_EVENT);
        ReplyKeyboardButton switchEventButton = new ReplyKeyboardButton().setText(SWITCH_EVENT);
        ReplyKeyboardButton showLastBillsButton = new ReplyKeyboardButton().setText(RECENT_BILLS);
        ReplyKeyboardButton eventDebtsButton = new ReplyKeyboardButton().setText(EVENT_DEBTS);
        ReplyKeyboardButton deleteBillButton = new ReplyKeyboardButton().setText(DELETE_BILL);
        ReplyKeyboardButton showTotalButton = new ReplyKeyboardButton().setText(EVENT_STATISTIC);
        ReplyKeyboardButton switchToSplitBill = new ReplyKeyboardButton().setText(BLACK_HEART + SWITCH_TO_SPLIT_BILL);

        ReplyKeyboardButton manageEvents = new ReplyKeyboardButton().setText(MANAGE_EVENTS);

        keyboard.addRow(sendMoneyButton, currentEventButton, switchEventButton);
        keyboard.addRow(deleteBillButton, showLastBillsButton, manageEvents);
        keyboard.addRow(eventDebtsButton, showTotalButton, switchToSplitBill);

        return keyboard;
    }
}
