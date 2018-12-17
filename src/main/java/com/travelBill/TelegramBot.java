package com.travelBill;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.ActionRouter;
import com.travelBill.telegram.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramUserService telegramUserService;
    private final ActionRouter actionRouter;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, ActionRouter actionRouter) {
        this.telegramUserService = telegramUserService;
        this.actionRouter = actionRouter;
    }

    @Override
    public void onUpdateReceived(Update update) {
        User currentUser = setupUser(update);
        SendMessage message = actionRouter.delegateAction(update, currentUser);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Bot username";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_KEY");
    }


    private User setupUser(Update update) {
        User currentUser = null;
        if (update.getMessage() != null) {
            currentUser = telegramUserService.setupUser(update.getMessage().getFrom());
        } else if (update.getCallbackQuery() != null) {
            currentUser = telegramUserService.setupUser(update.getCallbackQuery().getFrom());
        }
        return currentUser;
    }
}
