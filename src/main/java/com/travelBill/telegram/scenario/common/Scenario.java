package com.travelBill.telegram.scenario.common;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Scenario {
    SendMessage createMessage();
}
