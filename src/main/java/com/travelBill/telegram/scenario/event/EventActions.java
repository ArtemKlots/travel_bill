package com.travelBill.telegram.scenario.event;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public enum EventActions {
    CREATE, SHOW_ALL, SHOW_CURRENT, SWITCH;

    //todo Should be here? Should use Update?
    public static boolean isEventAction(Update update) {
        return defineType(update) != null;
    }

    //todo refactor it
    //todo Should be here? Should use Update?
    public static EventActions defineType(Update update) {
        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();

        if (message != null) {
            boolean isCreateEventSignal = message.getText().startsWith("/create");
            boolean isShowEventsSignal = message.getText().toLowerCase().equals("show events");
            boolean isShowCurrentEventSignal = message.getText().toLowerCase().equals("show current event");

            if (isCreateEventSignal) {
                return CREATE;
            }

            if (isShowEventsSignal) {
                return SHOW_ALL;
            }

            if (isShowCurrentEventSignal) {
                return SHOW_CURRENT;
            }

        }

        if (callbackQuery != null) {
            boolean isSwitchingEventSignal = callbackQuery.getData().toLowerCase().startsWith("switch_to_event");

            if (isSwitchingEventSignal) {
                return SWITCH;
            }
        }

        return null;
    }
}
