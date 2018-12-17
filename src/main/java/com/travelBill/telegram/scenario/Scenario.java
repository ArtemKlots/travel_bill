package com.travelBill.telegram.scenario;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Scenario {
    SendMessage perform();
}
