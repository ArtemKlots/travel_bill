package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.bill.add.AddBillScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.request.ShowBillsToDeleteScenario;
import com.travelBill.telegram.scenario.individual.bill.show.ShowDebtsScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class IndividualScenarioFactory {
    private final EventScenarioHelper eventScenarioHelper;
    private final BillScenarioHelper billScenarioHelper;
    private final ShowEventsListScenario showEventsListScenario;
    private final ShowCurrentEventScenario showCurrentEventScenario;
    private final SwitchEventScenario switchEventScenario;
    private final ShowLastTransactionsScenario showLastTransactionsScenario;
    private final AddBillScenario addBillScenario;
    private final DeleteBillScenario deleteBillScenario;
    private final ShowBillsToDeleteScenario showBillsToDeleteScenario;
    private final ShowDebtsScenario showDebtsScenario;


    @Autowired
    public IndividualScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                     BillScenarioHelper billScenarioHelper,
                                     ShowEventsListScenario showEventsListScenario,
                                     ShowCurrentEventScenario showCurrentEventScenario,
                                     SwitchEventScenario switchEventScenario,
                                     ShowLastTransactionsScenario showLastTransactionsScenario,
                                     AddBillScenario addBillScenario,
                                     DeleteBillScenario deleteBillScenario,
                                     ShowBillsToDeleteScenario showBillsToDeleteScenario,
                                     ShowDebtsScenario showDebtsScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
        this.showEventsListScenario = showEventsListScenario;
        this.showCurrentEventScenario = showCurrentEventScenario;
        this.switchEventScenario = switchEventScenario;
        this.showLastTransactionsScenario = showLastTransactionsScenario;
        this.addBillScenario = addBillScenario;
        this.deleteBillScenario = deleteBillScenario;
        this.showBillsToDeleteScenario = showBillsToDeleteScenario;
        this.showDebtsScenario = showDebtsScenario;
    }

    public Scenario createScenario(Request request) throws ScenarioNotFoundException {
        Scenario selectedScenario = null;

        if (eventScenarioHelper.isShowEventsSignal(request)) {
            selectedScenario = showEventsListScenario;
        }

        if (eventScenarioHelper.isSwitchEventSignal(request)) {
            selectedScenario = switchEventScenario;
        }

        if (eventScenarioHelper.isShowCurrentEventSignal(request)) {
            selectedScenario = showCurrentEventScenario;
        }

        if (billScenarioHelper.isShowLastTransactionsSignal(request)) {
            selectedScenario = showLastTransactionsScenario;
        }

        if (billScenarioHelper.isContribution(request)) {
            selectedScenario = addBillScenario;
        }


        if (billScenarioHelper.isDeleteBillRequestSignal(request)) {
            selectedScenario = showBillsToDeleteScenario;
        }

        if (billScenarioHelper.isDeleteBillConfirmationSignal(request)) {
            selectedScenario = deleteBillScenario;
        }

        if (billScenarioHelper.isShowDebtsSignal(request)) {
            selectedScenario = showDebtsScenario;
        }

        if (isNull(selectedScenario)) {
            throw new ScenarioNotFoundException();
        }

        return selectedScenario;
    }

}
