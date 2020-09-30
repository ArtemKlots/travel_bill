package com.travelBill.telegram.scenario.group;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.event.create.CreateEventScenario;
import com.travelBill.telegram.scenario.group.event.join.JoinEventScenario;
import com.travelBill.telegram.scenario.group.event.join.SilentJoinEventScenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class GroupScenarioFactory {
    private final EventScenarioHelper eventScenarioHelper;
    private final CreateEventScenario createEventScenario;
    private final JoinEventScenario joinEventScenario;
    private final SilentJoinEventScenario silentJoinEventScenario;

    public GroupScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                CreateEventScenario createEventScenario,
                                JoinEventScenario joinEventScenario, SilentJoinEventScenario silentJoinEventScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.createEventScenario = createEventScenario;
        this.joinEventScenario = joinEventScenario;
        this.silentJoinEventScenario = silentJoinEventScenario;
    }

    public Scenario createScenario(Request request) {
        Scenario selectedScenario = null;

        // TODO: it can be made using switch/case

        if (request.isGroupChatCreated) {
            selectedScenario = createEventScenario;
        }

        if (eventScenarioHelper.isJoinEventsSignal(request)) {
            selectedScenario = joinEventScenario;
        }

        if (isNull(selectedScenario)) {
            selectedScenario = silentJoinEventScenario;
        }

        return selectedScenario;
    }

}
