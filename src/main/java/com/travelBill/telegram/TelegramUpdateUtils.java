package com.travelBill.telegram;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramUpdateUtils {

    public Long getChatId(Update update) {
        return this.getChat(update).getId();
    }

    public Chat getChat(Update update) {
        Chat chat;
        if (update.hasMessage()) {
            chat = update.getMessage().getChat();
        } else {
            chat = update.getCallbackQuery().getMessage().getChat();
        }

        return chat;
    }
}
