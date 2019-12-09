package com.travelBill.telegram.scenario;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Response;

public class ClosedEventResponse extends Response {
    public ClosedEventResponse(Event event) {
        super.parseMode = ParseMode.MARKDOWN;
        super.message = String.format("❌ Your action was rejected, event *%s* is closed!", event.getTitle());
    }
}
