package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.event.create.CreateEventScenario;
import com.travelBill.telegram.scenario.group.event.join.JoinEventScenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class GroupScenarioFactory {
    private final EventScenarioHelper eventScenarioHelper;
    private final CreateEventScenario createEventScenario;
    private final JoinEventScenario joinEventScenario;

    public GroupScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                CreateEventScenario createEventScenario,
                                JoinEventScenario joinEventScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.createEventScenario = createEventScenario;
        this.joinEventScenario = joinEventScenario;
    }

    public Scenario createScenario(Request request, User currentUser) {
        Scenario selectedScenario = null;

        // TODO: it can be made using switch/case

        if (request.isGroupChatCreated) {
            selectedScenario = createEventScenario;
        }

        if (eventScenarioHelper.isJoinEventsSignal(request)) {
            selectedScenario = joinEventScenario;
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new IgnoreMessageScenario();
        }

        return selectedScenario;
    }

}
