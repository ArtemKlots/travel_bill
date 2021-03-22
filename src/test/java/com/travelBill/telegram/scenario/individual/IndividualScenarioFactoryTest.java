package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.DeletePreviousMessageScenario;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.bill.add.AddBillScenario;
import com.travelBill.telegram.scenario.individual.bill.debts.ShowDebtsScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.confirm.DeleteBillScenario;
import com.travelBill.telegram.scenario.individual.bill.delete.request.ShowBillsToDeleteScenario;
import com.travelBill.telegram.scenario.individual.bill.lastBills.ShowLastBillsScenario;
import com.travelBill.telegram.scenario.individual.debt.*;
import com.travelBill.telegram.scenario.individual.event.ShowCurrentEventScenario;
import com.travelBill.telegram.scenario.individual.event.ShowEventsListScenario;
import com.travelBill.telegram.scenario.individual.event.SwitchEventScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestCancelScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestScenario;
import com.travelBill.telegram.scenario.individual.event.close.CloseEventRequestSubmitScenario;
import com.travelBill.telegram.scenario.individual.event.totalSpent.ShowTotalSpentByEventScenario;
import com.travelBill.telegram.scenario.splitBill.SplitBillScenarioFactory;
import com.travelBill.telegram.user.state.UserState;
import com.travelBill.telegram.user.state.UserStateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.travelBill.telegram.user.state.State.SEND_MONEY;
import static com.travelBill.telegram.user.state.State.START;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndividualScenarioFactoryTest {
    private EventScenarioHelper eventScenarioHelper = mock(EventScenarioHelper.class);
    private BillScenarioHelper billScenarioHelper = mock(BillScenarioHelper.class);
    private ShowEventsListScenario showEventsListScenario = mock(ShowEventsListScenario.class);
    private ShowCurrentEventScenario showCurrentEventScenario = mock(ShowCurrentEventScenario.class);
    private SwitchEventScenario switchEventScenario = mock(SwitchEventScenario.class);
    private ShowLastBillsScenario showLastBillsScenario = mock(ShowLastBillsScenario.class);
    private AddBillScenario addBillScenario = mock(AddBillScenario.class);
    private DeleteBillScenario deleteBillScenario = mock(DeleteBillScenario.class);
    private DeletePreviousMessageScenario deletePreviousMessageScenarioMock = mock(DeletePreviousMessageScenario.class);
    private ShowBillsToDeleteScenario showBillsToDeleteScenario = mock(ShowBillsToDeleteScenario.class);
    private ShowDebtsScenario showDebtsScenario = mock(ShowDebtsScenario.class);
    private CloseEventRequestScenario closeEventRequestScenarioMock = mock(CloseEventRequestScenario.class);
    private CloseEventRequestSubmitScenario closeEventRequestSubmitScenarioMock = mock(CloseEventRequestSubmitScenario.class);
    private CloseEventRequestCancelScenario closeEventRequestCancelScenarioMock = mock(CloseEventRequestCancelScenario.class);
    private IndividualScenarioFactory individualScenarioFactory;
    private ShowTotalSpentByEventScenario showTotalSpentByEventScenarioMock;
    private GetAllContactsScenario getAllContactsScenario;
    private SelectDebtorScenario selectDebtorScenarioMock = mock(SelectDebtorScenario.class);
    private SendMoneyScenario sendMoneyScenarioMock = mock(SendMoneyScenario.class);
    private UserStateService userStateServiceMock = mock(UserStateService.class);
    private RequestAmountScenario requestAmountScenarioMock = mock(RequestAmountScenario.class);
    private ShowHistoryScenario showHistoryScenarioMock = mock(ShowHistoryScenario.class);
    private SplitBillScenarioFactory splitBillScenarioFactory = mock(SplitBillScenarioFactory.class);
    private Request request;
    private User user;

    @BeforeEach
    void setupContextProvider() {
        individualScenarioFactory = new IndividualScenarioFactory(
                eventScenarioHelper,
                billScenarioHelper,
                showEventsListScenario,
                showCurrentEventScenario,
                switchEventScenario,
                showLastBillsScenario,
                addBillScenario,
                deleteBillScenario,
                deletePreviousMessageScenarioMock,
                showBillsToDeleteScenario, showDebtsScenario,
                closeEventRequestScenarioMock,
                closeEventRequestCancelScenarioMock,
                closeEventRequestSubmitScenarioMock,
                showTotalSpentByEventScenarioMock,
                selectDebtorScenarioMock,
                getAllContactsScenario,
                sendMoneyScenarioMock,
                userStateServiceMock,
                requestAmountScenarioMock,
                showHistoryScenarioMock,
                splitBillScenarioFactory);

        user = new User();
        user.setId(1L);
        request = new Request();
        request.user = user;
    }

    @Test
    void createScenario_shouldReturnShowEventsListScenario_whenShowEventsSignalWasProvided() {
        when(eventScenarioHelper.isShowEventsSignal(request)).thenReturn(true);
        when(userStateServiceMock.get(request.user.getId())).thenReturn(null);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showEventsListScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnShowLastTransactionsScenario_whenShowLastTransactionsSignalWasProvided() {
        when(billScenarioHelper.isShowLastBillsSignal(request)).thenReturn(true);
        when(userStateServiceMock.get(request.user.getId())).thenReturn(null);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showLastBillsScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnAddBillScenario_whenContributionSignalWasProvided_andUserStateIsStart() {
        when(billScenarioHelper.isContribution(request)).thenReturn(true);
        when(userStateServiceMock.get(request.user.getId())).thenReturn(new UserState(user, START));
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(addBillScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnAddBillScenario_whenContributionSignalWasProvided_andUserStateIsSendMoney() {
        when(billScenarioHelper.isContribution(request)).thenReturn(true);
        when(userStateServiceMock.get(request.user.getId())).thenReturn(new UserState(user, SEND_MONEY));
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(sendMoneyScenarioMock.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldThrowScenarioNotFoundException_whenScenarioWasNotDefined() {
        when(userStateServiceMock.get(request.user.getId())).thenReturn(null);
        assertThrows(ScenarioNotFoundException.class, () -> individualScenarioFactory.createScenario(request));
    }

}