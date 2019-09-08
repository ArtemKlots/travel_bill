package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.bill.add.AddBillScenario;
import com.travelBill.telegram.scenario.group.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.group.bill.delete.request.ShowBillsToDeleteScenario;
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
    private final AddBillScenario addBillScenario;
    private final DeleteBillScenario deleteBillScenario;
    private final JoinEventScenario joinEventScenario;
    private final ShowDebtsScenario showDebtsScenario;
    private final ShowBillsToDeleteScenario showBillsToDeleteScenario;

    public GroupScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                BillScenarioHelper billScenarioHelper,
                                CreateEventScenario createEventScenario,
                                AddBillScenario addBillScenario, DeleteBillScenario deleteBillScenario,
                                JoinEventScenario joinEventScenario,
                                ShowDebtsScenario showDebtsScenario,
                                ShowBillsToDeleteScenario showBillsToDeleteScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
        this.createEventScenario = createEventScenario;
        this.addBillScenario = addBillScenario;
        this.deleteBillScenario = deleteBillScenario;
        this.joinEventScenario = joinEventScenario;
        this.showDebtsScenario = showDebtsScenario;
        this.showBillsToDeleteScenario = showBillsToDeleteScenario;
    }

    public Scenario createScenario(Request request, User currentUser) {
        Scenario selectedScenario = null;

        // TODO: it can be made using switch/case
        if (billScenarioHelper.isDeleteBillConfirmationSignal(request)) {
            selectedScenario = deleteBillScenario;
        }

        if (request.isGroupChatCreated) {
            selectedScenario = createEventScenario;
        }

        if (billScenarioHelper.isContribution(request)) {
            selectedScenario = addBillScenario;
        }

        if (eventScenarioHelper.isJoinEventsSignal(request)) {
            selectedScenario = joinEventScenario;
        }

        if (billScenarioHelper.isShowDebtsSignal(request)) {
            selectedScenario = showDebtsScenario;
        }

        if (billScenarioHelper.isDeleteBillRequestSignal(request)) {
            selectedScenario = showBillsToDeleteScenario;
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario();
        }

        return selectedScenario;
    }

}
