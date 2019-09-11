package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static com.travelBill.ParseMode.MARKDOWN;

@Service
public class SwitchEventScenario implements Scenario {
    private final UserService userService;
    private final BotApi botApi;

    public SwitchEventScenario(UserService userService, BotApi botApi) {
        this.userService = userService;
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        Long eventId = extractEventId(request);
        User updatedUser = userService.changeCurrentEvent(request.user.getId(), eventId);
        botApi.deleteMessage(request.chatId, request.messageId);

        return buildResponse(updatedUser);
    }

    private Long extractEventId(Request request) {
        String[] chunks = request.callbackQueryData.split("-");

        if (chunks.length != 2) {
            throw new IllegalArgumentException("Incorrect callback format. Expected switch_to_event-N, where N is event id");
        }
        return Long.parseLong(chunks[1]);
    }

    private Response buildResponse(User updatedUser) {
        Response response = new Response();
        response.parseMode = MARKDOWN;
        response.message = String.format("You successfully switched to the event *%s*", updatedUser.getCurrentEvent().getTitle());
        return response;
    }
}
