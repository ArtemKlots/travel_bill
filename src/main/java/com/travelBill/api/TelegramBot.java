package com.travelBill.api;

import com.travelBill.api.core.User;
import com.travelBill.api.telegram.TelegramUserService;
import com.travelBill.api.telegram.scenario.ScenarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramUserService telegramUserService;
    private final ScenarioManager scenarioManager;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, ScenarioManager scenarioManager) {
        this.telegramUserService = telegramUserService;
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = null;
        if (update.getMessage() != null) {
            Message updateMessage = update.getMessage();
            User currentUser = telegramUserService.setupUser(updateMessage.getFrom());

            boolean isStartSignal = updateMessage.getText().equals("/start");
            boolean isShowEventsSignal = updateMessage.getText().toLowerCase().equals("show events");
            boolean isCreateEventSignal = updateMessage.getText().startsWith("/create");

            if (isStartSignal) {
                message = scenarioManager.performInitialScenatio(update);
            }

            if (isShowEventsSignal) {
                message = scenarioManager.getAllEvents(update, currentUser);
            }

            if (isCreateEventSignal) {
                message = scenarioManager.createEvent(update, currentUser);
            }
        }

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
}
