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
import com.travelBill.telegram.scenario.individual.event.totalSpent.ShowTotalSpentByEventScenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private ShowBillsToDeleteScenario showBillsToDeleteScenario = mock(ShowBillsToDeleteScenario.class);
    private ShowDebtsScenario showDebtsScenario = mock(ShowDebtsScenario.class);
    private Request request = mock(Request.class);
    private IndividualScenarioFactory individualScenarioFactory;
    private ShowTotalSpentByEventScenario showTotalSpentByEventScenario;

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
                showBillsToDeleteScenario,
                showDebtsScenario,
                showTotalSpentByEventScenario);
    }

    @Test
    void createScenario_shouldReturnShowEventsListScenario_whenShowEventsSignalWasProvided() {
        when(eventScenarioHelper.isShowEventsSignal(request)).thenReturn(true);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showEventsListScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnShowLastTransactionsScenario_whenShowLastTransactionsSignalWasProvided() {
        when(billScenarioHelper.isShowLastBillsSignal(request)).thenReturn(true);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showLastBillsScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldThrowScenarioNotFoundException_whenScenarioWasNotDefined() {
        assertThrows(ScenarioNotFoundException.class, () -> individualScenarioFactory.createScenario(request));
    }

}