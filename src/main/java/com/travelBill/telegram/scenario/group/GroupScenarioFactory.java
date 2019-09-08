package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.bill.show.ShowDebtsScenario;
import com.travelBill.telegram.scenario.group.event.create.CreateEventScenario;
import com.travelBill.telegram.scenario.group.event.join.JoinEventScenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class GroupScenarioFactory {
    private final EventScenarioHelper eventScenarioHelper;
    private final BillScenarioHelper billScenarioHelper;
    private final CreateEventScenario createEventScenario;
    private final JoinEventScenario joinEventScenario;
    private final ShowDebtsScenario showDebtsScenario;

    public GroupScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                BillScenarioHelper billScenarioHelper,
                                CreateEventScenario createEventScenario,
                                JoinEventScenario joinEventScenario,
                                ShowDebtsScenario showDebtsScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
        this.createEventScenario = createEventScenario;
        this.joinEventScenario = joinEventScenario;
        this.showDebtsScenario = showDebtsScenario;
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

        if (billScenarioHelper.isShowDebtsSignal(request)) {
            selectedScenario = showDebtsScenario;
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario();
        }

        return selectedScenario;
    }

}
