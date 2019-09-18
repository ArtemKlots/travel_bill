package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import org.springframework.stereotype.Service;

@Service
public class GroupChatKeyboardBuilder {

    public ReplyKeyboard build() {
        ReplyKeyboard keyboard = new ReplyKeyboard();
        keyboard.isDeleteKeyboard = true;

        return keyboard;
    }
}
