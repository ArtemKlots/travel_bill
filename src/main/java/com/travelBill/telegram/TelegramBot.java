package com.travelBill.telegram;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.ScenarioFactory;
import com.travelBill.telegram.user.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramUserService telegramUserService;
    //todo is it ok to inject a factory?
    private final ScenarioFactory scenarioFactory;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, ScenarioFactory scenarioFactory) {
        this.telegramUserService = telegramUserService;
        this.scenarioFactory = scenarioFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        User currentUser = setupUser(update);
        SendMessage message = scenarioFactory.getScenario(update, currentUser).perform();

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
