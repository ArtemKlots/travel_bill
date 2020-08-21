package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.DeletePreviousMessageScenario;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.*;
import com.travelBill.telegram.scenario.individual.bill.add.AddBillScenario;
import com.travelBill.telegram.scenario.individual.bill.debts.ShowDebtsScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.request.ShowBillsToDeleteScenario;
import com.travelBill.telegram.scenario.individual.bill.lastBills.ShowLastBillsScenario;
import com.travelBill.telegram.scenario.individual.debt.GetAllContactsScenario;
import com.travelBill.telegram.scenario.individual.debt.RequestAmountScenario;
import com.travelBill.telegram.scenario.individual.debt.SelectDebtorScenario;
import com.travelBill.telegram.scenario.individual.debt.SendMoneyScenario;
import com.travelBill.telegram.scenario.individual.event.*;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestCancelScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestSubmitScenario;
import com.travelBill.telegram.scenario.individual.event.totalSpent.ShowTotalSpentByEventScenario;
import com.travelBill.telegram.scenario.navigation.GoBackScenario;
import com.travelBill.telegram.user.state.UserState;
import com.travelBill.telegram.user.state.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.user.state.State.SEND_MONEY;
import static com.travelBill.telegram.user.state.State.START;
import static java.util.Objects.isNull;

@Service
public class IndividualScenarioFactory {
    private final EventScenarioHelper eventScenarioHelper;
    private final BillScenarioHelper billScenarioHelper;
    private final DebtScenarioHelper debtScenarioHelper = new DebtScenarioHelper();
    private final ShowEventsListScenario showEventsListScenario;
    private final ShowCurrentEventScenario showCurrentEventScenario;
    private final SwitchEventScenario switchEventScenario;
    private final ShowLastBillsScenario showLastBillsScenario;
    private final AddBillScenario addBillScenario;
    private final DeleteBillScenario deleteBillScenario;
    private final DeletePreviousMessageScenario deletePreviousMessageScenario;
    private final ShowBillsToDeleteScenario showBillsToDeleteScenario;
    private final ShowDebtsScenario showDebtsScenario;
    private final CloseEventRequestScenario closeEventRequestScenario;
    private final CloseEventRequestCancelScenario closeEventRequestCancelScenario;
    private final CloseEventRequestSubmitScenario closeEventRequestSubmitScenario;
    private final ShowTotalSpentByEventScenario showTotalSpentByEventScenario;
    private final SelectDebtorScenario selectDebtorScenario;
    private final GetAllContactsScenario getAllContactsScenario;
    private final SendMoneyScenario sendMoneyScenario;
    private final ManageEventScenario manageEventScenario = new ManageEventScenario();
    private final NavigationScenarioHelper navigationScenarioHelper = new NavigationScenarioHelper();
    private final GoBackScenario goBackScenario = new GoBackScenario();
    private final ShowClosedEventsListScenario showClosedEventsListScenario = new ShowClosedEventsListScenario();
    private final UserStateService userStateService;
    private final RequestAmountScenario requestAmountScenario;


    @Autowired
    public IndividualScenarioFactory(EventScenarioHelper eventScenarioHelper,
                                     BillScenarioHelper billScenarioHelper,
                                     ShowEventsListScenario showEventsListScenario,
                                     ShowCurrentEventScenario showCurrentEventScenario,
                                     SwitchEventScenario switchEventScenario,
                                     ShowLastBillsScenario showLastBillsScenario,
                                     AddBillScenario addBillScenario,
                                     DeleteBillScenario deleteBillScenario,
                                     DeletePreviousMessageScenario deletePreviousMessageScenario,
                                     ShowBillsToDeleteScenario showBillsToDeleteScenario,
                                     ShowDebtsScenario showDebtsScenario,
                                     CloseEventRequestScenario closeEventRequestScenario,
                                     CloseEventRequestCancelScenario closeEventRequestCancelScenario,
                                     CloseEventRequestSubmitScenario closeEventRequestSubmitScenario,
                                     ShowTotalSpentByEventScenario showTotalSpentByEventScenario,
                                     SelectDebtorScenario selectDebtorScenario,
                                     GetAllContactsScenario getAllContactsScenario, SendMoneyScenario sendMoneyScenario,
                                     UserStateService userStateService,
                                     RequestAmountScenario requestAmountScenario) {
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
        this.showEventsListScenario = showEventsListScenario;
        this.showCurrentEventScenario = showCurrentEventScenario;
        this.switchEventScenario = switchEventScenario;
        this.showLastBillsScenario = showLastBillsScenario;
        this.addBillScenario = addBillScenario;
        this.deleteBillScenario = deleteBillScenario;
        this.deletePreviousMessageScenario = deletePreviousMessageScenario;
        this.showBillsToDeleteScenario = showBillsToDeleteScenario;
        this.showDebtsScenario = showDebtsScenario;
        this.closeEventRequestScenario = closeEventRequestScenario;
        this.closeEventRequestCancelScenario = closeEventRequestCancelScenario;
        this.closeEventRequestSubmitScenario = closeEventRequestSubmitScenario;
        this.showTotalSpentByEventScenario = showTotalSpentByEventScenario;
        this.selectDebtorScenario = selectDebtorScenario;
        this.getAllContactsScenario = getAllContactsScenario;
        this.sendMoneyScenario = sendMoneyScenario;
        this.userStateService = userStateService;
        this.requestAmountScenario = requestAmountScenario;
    }

    public Scenario createScenario(Request request) throws ScenarioNotFoundException {
        Scenario selectedScenario = null;

        if (request.hasCallbackQueryData() && request.callbackQueryData.equals("delete_previous_message")) {
            selectedScenario = deletePreviousMessageScenario;
        }

        if (eventScenarioHelper.isShowEventsSignal(request)) {
            selectedScenario = showEventsListScenario;
        }

        if (eventScenarioHelper.isSwitchEventSignal(request)) {
            selectedScenario = switchEventScenario;
        }

        if (eventScenarioHelper.isCancelEventSwitchingSignal(request)) {
            selectedScenario = deletePreviousMessageScenario;
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

        if (eventScenarioHelper.isManageEventsRequest(request)) {
            selectedScenario = manageEventScenario;
        }

        if (eventScenarioHelper.isSwitchToClosedEventRequest(request)) {
            selectedScenario = showClosedEventsListScenario;
        }

        if (billScenarioHelper.isShowLastBillsSignal(request)) {
            selectedScenario = showLastBillsScenario;
        }

        if (billScenarioHelper.isDeleteBillRequestSignal(request)) {
            selectedScenario = showBillsToDeleteScenario;
        }

        if (billScenarioHelper.isDeleteBillConfirmationSignal(request)) {
            selectedScenario = deleteBillScenario;
        }

        if (billScenarioHelper.isDeleteBillCancellationSignal(request)) {
            selectedScenario = deletePreviousMessageScenario;
        }

        if (billScenarioHelper.isShowDebtsSignal(request)) {
            selectedScenario = showDebtsScenario;
        }

        if (billScenarioHelper.isShowTotalSignal(request)) {
            selectedScenario = showTotalSpentByEventScenario;
        }

        if (debtScenarioHelper.isSelectDebtorRequest(request)) {
            selectedScenario = selectDebtorScenario;
        }

        if (debtScenarioHelper.isRequestAmountRequest(request)) {
            selectedScenario = requestAmountScenario;
        }

        if (debtScenarioHelper.isAllContactsRequest(request)) {
            selectedScenario = getAllContactsScenario;
        }

        if (navigationScenarioHelper.isNavigationBack(request)) {
            selectedScenario = goBackScenario;
        }

        UserState userState = userStateService.get(request.user.getId());
        if (billScenarioHelper.isContribution(request) && (userState == null || userState.getState() == START)) {
            selectedScenario = addBillScenario;
        }

        if (billScenarioHelper.isContribution(request) && userState != null && userState.getState() == SEND_MONEY) {
            selectedScenario = sendMoneyScenario;
        }

        if (isNull(selectedScenario)) {
            throw new ScenarioNotFoundException();
        }

        return selectedScenario;
    }

}
