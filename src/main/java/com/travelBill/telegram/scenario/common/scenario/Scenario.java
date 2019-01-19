package com.travelBill.telegram.scenario.common.scenario;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Scenario {
    SendMessage createMessage();
}
