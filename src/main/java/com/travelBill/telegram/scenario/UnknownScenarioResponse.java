package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class UnknownScenarioResponse implements Response {
    @Override
    public String getMessage() {
        return "Sorry, but I can't understand you :( Could you rephrase please?";
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
