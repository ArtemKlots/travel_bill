package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.UserCommand.*;

@Service
public class EventScenarioHelper {
    //TODO extract duplicated strings in one place

    public boolean isShowEventsSignal(Request request) {
        return request.hasMessage() && request.message.equals(SWITCH_EVENT);
    }

    public boolean isSwitchEventSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("switch_to_event-");
    }

    public boolean isCancelEventSwitchingSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.equals("cancel_event_switching");
    }

    public boolean isCloseEventCancelSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("close_event_request_cancel");
    }


    public boolean isCloseEventSumbitSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("close_event_request_submit");
    }

    public boolean isJoinEventsSignal(Request request) {
        return request.hasMessage() && request.message.toLowerCase().startsWith("/join");
    }

    public boolean isShowCurrentEventSignal(Request request) {
        return request.hasMessage() && request.message.equals(CURRENT_EVENT);
    }

    public boolean isCloseEventRequest(Request request) {
        return request.hasMessage() && request.message.equals(CLOSE_EVENT);
    }

    public boolean isManageEventsRequest(Request request) {
        return request.hasMessage() && request.message.equals(MANAGE_EVENTS);
    }

    public boolean isSwitchToClosedEventRequest(Request request) {
        return request.hasMessage() && request.message.contains(SWITCH_TO_CLOSED_EVENT);
    }
}
