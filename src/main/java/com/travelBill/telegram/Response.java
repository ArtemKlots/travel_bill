package com.travelBill.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

// todo make interface
public interface Response {
    String parseMode = null;

    String getMessage();

    ReplyKeyboard getKeyboard();
}
