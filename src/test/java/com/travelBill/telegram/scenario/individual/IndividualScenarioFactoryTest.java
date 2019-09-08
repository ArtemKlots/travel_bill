package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
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
    private ShowLastTransactionsScenario showLastTransactionsScenario = mock(ShowLastTransactionsScenario.class);
    private Request request = mock(Request.class);
    private IndividualScenarioFactory individualScenarioFactory;

    @BeforeEach
    void setupContextProvider() {
        individualScenarioFactory = new IndividualScenarioFactory(
                eventScenarioHelper,
                billScenarioHelper,
                showEventsListScenario,
                showCurrentEventScenario,
                switchEventScenario,
                showLastTransactionsScenario);
    }

    @Test
    void createScenario_shouldReturnShowEventsListScenario_whenShowEventsSignalWasProvided() {
        when(eventScenarioHelper.isShowEventsSignal(request)).thenReturn(true);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showEventsListScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnShowLastTransactionsScenario_whenShowLastTransactionsSignalWasProvided() {
        when(billScenarioHelper.isShowLastTransactionsSignal(request)).thenReturn(true);
        Scenario scenario = individualScenarioFactory.createScenario(request);
        assertEquals(showLastTransactionsScenario.getClass(), scenario.getClass());
    }

    @Test
    void createScenario_shouldThrowScenarioNotFoundException_whenScenarioWasNotDefined() {
        assertThrows(ScenarioNotFoundException.class, () -> individualScenarioFactory.createScenario(request));
    }

}