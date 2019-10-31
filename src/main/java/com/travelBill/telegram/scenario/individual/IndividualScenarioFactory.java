package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.bill.add.AddBillScenario;
import com.travelBill.telegram.scenario.individual.bill.debts.ShowDebtsScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.request.ShowBillsToDeleteScenario;
import com.travelBill.telegram.scenario.individual.bill.lastBills.ShowLastBillsScenario;
import com.travelBill.telegram.scenario.individual.event.ShowCurrentEventScenario;
import com.travelBill.telegram.scenario.individual.event.ShowEventsListScenario;
import com.travelBill.telegram.scenario.individual.event.SwitchEventScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestCancelScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestSubmitScenario;
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
    private final ShowLastBillsScenario showLastBillsScenario;
    private final AddBillScenario addBillScenario;
    private final DeleteBillScenario deleteBillScenario;
    private final ShowBillsToDeleteScenario showBillsToDeleteScenario;
    private final ShowDebtsScenario showDebtsScenario;
    private final CloseEventRequestScenario closeEventRequestScenario;
    private final CloseEventRequestCancelScenario closeEventRequestCancelScenario;
    private final CloseEventRequestSubmitScenario closeEventRequestSubmitScenario;


    @Autowired
    public IndividualScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                     BillScenarioHelper billScenarioHelper,
                                     ShowEventsListScenario showEventsListScenario,
                                     ShowCurrentEventScenario showCurrentEventScenario,
                                     SwitchEventScenario switchEventScenario,
                                     ShowLastBillsScenario showLastBillsScenario,
                                     AddBillScenario addBillScenario,
                                     DeleteBillScenario deleteBillScenario,
                                     ShowBillsToDeleteScenario showBillsToDeleteScenario,
                                     ShowDebtsScenario showDebtsScenario,
                                     CloseEventRequestScenario closeEventRequestScenario,
                                     CloseEventRequestCancelScenario closeEventRequestCancelScenario,
                                     CloseEventRequestSubmitScenario closeEventRequestSubmitScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
        this.showEventsListScenario = showEventsListScenario;
        this.showCurrentEventScenario = showCurrentEventScenario;
        this.switchEventScenario = switchEventScenario;
        this.showLastBillsScenario = showLastBillsScenario;
        this.addBillScenario = addBillScenario;
        this.deleteBillScenario = deleteBillScenario;
        this.showBillsToDeleteScenario = showBillsToDeleteScenario;
        this.showDebtsScenario = showDebtsScenario;
        this.closeEventRequestScenario = closeEventRequestScenario;
        this.closeEventRequestCancelScenario = closeEventRequestCancelScenario;
        this.closeEventRequestSubmitScenario = closeEventRequestSubmitScenario;
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

        if (eventScenarioHelper.isCloseEventRequest(request)) {
            selectedScenario = closeEventRequestScenario;
        }

        if (eventScenarioHelper.isCloseEventCancelSignal(request)) {
            selectedScenario = closeEventRequestCancelScenario;
        }

        if (eventScenarioHelper.isCloseEventSumbitSignal(request)) {
            selectedScenario = closeEventRequestSubmitScenario;
        }

        if (billScenarioHelper.isShowLastBillsSignal(request)) {
            selectedScenario = showLastBillsScenario;
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
