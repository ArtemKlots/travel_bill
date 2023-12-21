package com.travelBill.telegram.bot;

import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.RollbarLogger;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseSendMessageMapper;
import com.travelBill.telegram.driver.UpdateRequestMapper;
import com.travelBill.telegram.exceptions.UserIsNotSetUpException;
import com.travelBill.telegram.scenario.ScenarioFactory;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.user.ActivityService;
import com.travelBill.telegram.user.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

@Component
@Conditional(IsWebhookCondition.class)
public class TelegramWebhookBot extends org.telegram.telegrambots.bots.TelegramWebhookBot {

    private final TelegramUserService telegramUserService;
    private final ActivityService activityService;
    private final ScenarioFactory scenarioFactory;
    private final ApplicationConfiguration applicationConfiguration;
    private final RollbarLogger rollbarLogger;

    private final Set<Integer> receivedUpdates = new HashSet<>();

    @Autowired
    public TelegramWebhookBot(TelegramUserService telegramUserService,
                       ActivityService activityService,
                       ScenarioFactory scenarioFactory,
                       ApplicationConfiguration applicationConfiguration,
                       RollbarLogger rollbarLogger) {
        this.telegramUserService = telegramUserService;
        this.activityService = activityService;
        this.scenarioFactory = scenarioFactory;
        this.applicationConfiguration = applicationConfiguration;
        this.rollbarLogger = rollbarLogger;
    }
    @Override
    public String getBotUsername() {
        return "Bot username";
    }

    @Override
    public String getBotToken() {
        return applicationConfiguration.getTelegramKey();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        // Telegram can send duplicated messages in webhook mode. And these messages are basically empty -> case errors
        // So, this is caching mechanism
        // https://core.telegram.org/bots/api#update
        if (receivedUpdates.contains(update.getUpdateId())) {
            System.out.printf("Ignoring duplicated update #%s. (%s)%n", update.getUpdateId(), update.toString());
            return null;
        } else {
            receivedUpdates.add(update.getUpdateId());
        }

        Request request;
        try {
            request = new UpdateRequestMapper().mapTo(update);
            System.out.println(request);
        } catch (Exception e) {
            rollbarLogger.error(e, update.toString());
            throw new RuntimeException("Unknown message");
        }

        try {
            request.user = setupUser(update);
            Response response = scenarioFactory.createScenario(request).execute(request);

            if (!response.isEmpty()) {
                SendMessage message = new ResponseSendMessageMapper().mapTo(response, request.chatId);
                execute(message);
            }
            activityService.registerActivity(request);
        } catch (AccessDeniedException e) {
            rollbarLogger.warn(e, update.toString());
            respondWithMessage(request, e.getMessage());
        } catch (ScenarioNotFoundException e) {
            e.printStackTrace();
            rollbarLogger.warn(e, update.toString());
            respondWithError(request);
        } catch (Exception e) {
            e.printStackTrace();
            rollbarLogger.error(e, update.toString());
            respondWithError(request);
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return "telegram";
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

    private void respondWithMessage(Request request, String message) {
        Response response = new Response(message);
        SendMessage sendMessage = new ResponseSendMessageMapper().mapTo(response, request.chatId);
        sneak(() -> execute(sendMessage));
    }
}
