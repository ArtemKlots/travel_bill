package com.travelBill.telegram.scenario.common.scenario;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BillScenarioHelper {

    public boolean isBillAction(Update update) {
        return isShowLastTransactionsSignal(update);
    }

    public boolean isShowLastTransactionsSignal(Update update) {
        return hasMessageAndText(update) && update.getMessage().getText().startsWith("/show_last_transactions");
    }

    // TODO: 27.03.19 Can be extracted
    public boolean hasMessageAndText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
