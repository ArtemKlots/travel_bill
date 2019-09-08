package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.Request;
import org.springframework.stereotype.Service;

@Service
public class EventScenarioHelper {

    public boolean isShowEventsSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show events");
    }

    public boolean isJoinEventsSignal(Request request) {
        return request.hasMessage() && request.message.toLowerCase().startsWith("/join");
    }

    public boolean isShowCurrentEventSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show current event");
    }
}
