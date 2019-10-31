package com.travelBill.telegram.scenario.individual.event.close;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;
import static java.util.Objects.isNull;

@Service
public class CloseEventRequestScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        Event currentEvent = request.user.getCurrentEvent();
        Response response = new Response(String.format("Are you sure that you would like to close event *%s*", currentEvent.getTitle()));
        response.parseMode = MARKDOWN;

        InlineKeyboard inlineKeyboard = new InlineKeyboard();
        inlineKeyboard.addRow(
                new InlineKeyboardButton().setText("✅ Yes").setCallbackData("close_event_request_submit-" + currentEvent.getId()),
                new InlineKeyboardButton().setText("❌ No").setCallbackData("close_event_request_cancel")
        );

        response.inlineKeyboard = inlineKeyboard;

        return response;
    }

}
