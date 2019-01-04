package com.travelBill.telegram.scenario.event;

import org.telegram.telegrambots.meta.api.objects.Update;

public class EventScenarioHelper {
    private Update update;

    public EventScenarioHelper(Update update) {
        this.update = update;
    }

    public boolean isEventAction() {
        return isCreateEventSignal()
                || isShowCurrentEventSignal()
                || isShowEventsSignal()
                || isSwitchingEventSignal();
    }

    boolean isCreateEventSignal() {
        return update.hasMessage() && update.getMessage().getText().startsWith("/create");
    }

    boolean isShowEventsSignal() {
        return update.hasMessage() && update.getMessage().getText().toLowerCase().equals("show events");
    }

    boolean isShowCurrentEventSignal() {
        return update.hasMessage() && update.getMessage().getText().toLowerCase().equals("show current event");
    }

    boolean isSwitchingEventSignal() {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().toLowerCase().startsWith("switch_to_event");
    }
}
