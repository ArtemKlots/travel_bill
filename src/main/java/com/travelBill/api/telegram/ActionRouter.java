package com.travelBill.api.telegram;

import com.travelBill.api.core.User;
import com.travelBill.api.telegram.scenario.ScenarioManager;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ActionRouter {
    private final ScenarioManager scenarioManager;

    public ActionRouter(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    public SendMessage delegateAction(Update update, User currentUser) {
        SendMessage message = null;
        if (update.getMessage() != null) {
            Message updateMessage = update.getMessage();

            boolean isStartSignal = updateMessage.getText().equals("/start");
            boolean isCreateEventSignal = updateMessage.getText().startsWith("/create");
            boolean isShowEventsSignal = updateMessage.getText().toLowerCase().equals("show events");
            boolean isShowCurrentEventSignal = updateMessage.getText().toLowerCase().equals("show current event");

            if (isStartSignal) {
                message = scenarioManager.performInitialScenatio(update);
            }

            if (isShowEventsSignal) {
                message = scenarioManager.getAllEvents(update, currentUser);
            }

            if (isCreateEventSignal) {
                message = scenarioManager.createEvent(update, currentUser);
            }

            if (isShowCurrentEventSignal) {
                message = scenarioManager.getCurrentEvent(update, currentUser);
            }
        } else {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            boolean isSwitchingEventSignal = callbackQuery.getData().toLowerCase().startsWith("switch_to_event");

            if (isSwitchingEventSignal) {
                message = scenarioManager.switchCurrentEvent(update, currentUser);
            }

        }
        return message;
    }
}
