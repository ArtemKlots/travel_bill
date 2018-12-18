package com.travelBill.telegram.scenario;

import com.travelBill.telegram.scenario.event.EventActions;
import org.telegram.telegrambots.meta.api.objects.Update;

public enum ScenarioTypes {
    START, EVENT;

    public static ScenarioTypes defineType(Update update) {
        if (EventActions.isEventAction(update)) {
            return EVENT;
        }

        if (update.getMessage().getText().equals("/start")) {
            return START;
        }

        return null;
    }
}
