package com.travelBill.telegram.scenario.common.scenario;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class NewEventScenarioHelper {

    public boolean isShowEventsSignal(Update update) {
        return hasMessageAndText(update) && update.getMessage().getText().toLowerCase().equals("show events");
    }

    // TODO: 27.03.19 Can be extracted
    public boolean hasMessageAndText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
