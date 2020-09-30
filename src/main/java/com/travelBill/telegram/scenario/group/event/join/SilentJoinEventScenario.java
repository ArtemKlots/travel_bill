package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.IgnoreMessageScenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class SilentJoinEventScenario implements Scenario {
    private final EventService eventService;
    private final JoinEventScenario joinEventScenario;


    public SilentJoinEventScenario(EventService eventService, JoinEventScenario joinEventScenario) {
        this.eventService = eventService;
        this.joinEventScenario = joinEventScenario;
    }

    public Response execute(Request request) {
        Event event = eventService.findByTelegramChatId(request.chatId);
        User existingUser = event.getMembers().stream()
                .filter(user -> user.getId().equals(request.user.getId()))
                .findAny()
                .orElse(null);

        if (isNull(existingUser)) {
            return joinEventScenario.execute(request);
        } else {
            return new IgnoreMessageScenario().execute(request);
        }
    }
}
