package com.travelBill.telegram.scenario.common.scenario;

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
                || isJoinEventsSignal()
                || isSwitchingEventSignal();
    }

    public boolean isCreateEventSignal() {
        return hasMessageAndText(update) && update.getMessage().getText().startsWith("/create");
    }

    public boolean isShowEventsSignal() {
        return hasMessageAndText(update) && update.getMessage().getText().toLowerCase().equals("show events");
    }

    public boolean isShowCurrentEventSignal() {
        return hasMessageAndText(update) && update.getMessage().getText().toLowerCase().equals("show current event");
    }

    public boolean isJoinEventsSignal() {
        return hasMessageAndText(update) && update.getMessage().getText().toLowerCase().startsWith("/join");
    }

    public boolean isSwitchingEventSignal() {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().toLowerCase().startsWith("switch_to_event");
    }

    public boolean hasMessageAndText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
