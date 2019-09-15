package com.travelBill.telegram;

import com.travelBill.api.core.user.User;
import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseSendMessageMapper;
import com.travelBill.telegram.driver.UpdateRequestMapper;
import com.travelBill.telegram.exceptions.UserIsNotSetUpException;
import com.travelBill.telegram.scenario.ScenarioFactory;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.user.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramUserService telegramUserService;
    private final ScenarioFactory scenarioFactory;
    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, ScenarioFactory scenarioFactory, ApplicationConfiguration applicationConfiguration) {
        this.telegramUserService = telegramUserService;
        this.scenarioFactory = scenarioFactory;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Request request = new UpdateRequestMapper().mapTo(update);
        try {
            request.user = setupUser(update);
            Response response = scenarioFactory.createScenario(request).execute(request);

            if (!response.isEmpty()) {
                SendMessage message = new ResponseSendMessageMapper().mapTo(response, request.chatId);
                execute(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            respondWithError(request);
        }
    }

    @Override
    public String getBotUsername() {
        return "Bot username";
    }

    @Override
    public String getBotToken() {
        return applicationConfiguration.getTelegramKey();
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

    private void respondWithError(Request request) {
        Response response = new UnknownScenario().execute(request);
        SendMessage sendMessage = new ResponseSendMessageMapper().mapTo(response, request.chatId);
        sneak(() -> execute(sendMessage));
    }

}
