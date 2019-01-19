package com.travelBill.telegram.scenario.common.scenario;

import org.telegram.telegrambots.meta.api.objects.Update;

public class InitialScenarioHelper {
    public static boolean isStart(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals("/start");
    }
}
