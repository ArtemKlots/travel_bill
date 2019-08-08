package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.bill.AddBillScenario;
import com.travelBill.telegram.scenario.group.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.group.bill.delete.request.ShowBillsToDeleteScenario;
import com.travelBill.telegram.scenario.group.bill.show.ShowDebtsScenario;
import com.travelBill.telegram.scenario.group.event.CreateEventScenario;
import com.travelBill.telegram.scenario.group.event.JoinEventScenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class GroupScenarioFactory {
    private final ContextProvider contextProvider;
    private final EventScenarioHelper eventScenarioHelper;
    private final BillScenarioHelper billScenarioHelper;

    public GroupScenarioFactory(ContextProvider contextProvider,
                                EventScenarioHelper eventScenarioHelper, BillScenarioHelper billScenarioHelper) {
        this.contextProvider = contextProvider;
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
    }

    public Scenario createScenario(Request request, User currentUser) {
        EventContext eventContext = contextProvider.getEventContext(request, currentUser);
        BillContext billContext = contextProvider.getBillContext(request, currentUser);
        Scenario selectedScenario = null;

        // TODO: it can be made using switch/case
        if (billScenarioHelper.isDeleteBillConfirmationSignal(request)) {
            selectedScenario = new DeleteBillScenario(billContext);
        }

        if (request.isGroupChatCreated) {
            selectedScenario = new CreateEventScenario(eventContext);
        }

        if (billScenarioHelper.isContribution(request)) {
            selectedScenario = new AddBillScenario(billContext);
        }

        if (eventScenarioHelper.isJoinEventsSignal(request)) {
            selectedScenario = new JoinEventScenario(eventContext);
        }

        if (billScenarioHelper.isShowDebtsSignal(request)) {
            selectedScenario = new ShowDebtsScenario(billContext);
        }

        if (billScenarioHelper.isDeleteBillRequestSignal(request)) {
            selectedScenario = new ShowBillsToDeleteScenario(billContext);
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario(request);
        }

        return selectedScenario;
    }

}
