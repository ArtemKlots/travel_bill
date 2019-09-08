package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.Request;
import org.springframework.stereotype.Service;

@Service
public class EventScenarioHelper {
    //TODO extract duplicated strings in one place

    public boolean isShowEventsSignal(Request request) {
        return request.hasMessage() && request.message.equals("Switch event");
    }

    public boolean isSwitchEventSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("switch_to_event-");
    }

    public boolean isJoinEventsSignal(Request request) {
        return request.hasMessage() && request.message.toLowerCase().startsWith("/join");
    }

    public boolean isShowCurrentEventSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show current event");
    }
}
