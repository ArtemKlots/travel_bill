package com.travelBill.telegram;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.exceptions.UserIsNotSetUpException;
import com.travelBill.telegram.scenario.ScenarioFactory;
import com.travelBill.telegram.scenario.UnknownScenario;
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
    private final ScenarioFactory scenarioFactory;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, ScenarioFactory scenarioFactory) {
        this.telegramUserService = telegramUserService;
        this.scenarioFactory = scenarioFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            User currentUser = setupUser(update);
            SendMessage message = scenarioFactory.createScenario(update, currentUser).createMessage();
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
            respondWithError(update);
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


    private User setupUser(Update update) throws UserIsNotSetUpException {
        org.telegram.telegrambots.meta.api.objects.User user;
        if (update.hasMessage()) {
            user = update.getMessage().getFrom();

        } else if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        } else {
            throw new UserIsNotSetUpException();
        }

        return telegramUserService.setupUser(user);
    }

    private void respondWithError(Update update) {
        SendMessage message = new UnknownScenario(update).createMessage();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
