package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndividualScenarioFactoryTest {
    private ContextProvider contextProvider = mock(ContextProvider.class);
    private EventScenarioHelper eventScenarioHelper = mock(EventScenarioHelper.class);
    private BillScenarioHelper billScenarioHelper = mock(BillScenarioHelper.class);
    private Request request = mock(Request.class);
    private User user = mock(User.class);

    @Spy
    EventContext eventContext;
    @Spy
    BillContext billContext;

    @Before
    void setupContextProvider() {
        when(contextProvider.getBillContext(any(), any())).thenReturn(billContext);
        when(contextProvider.getEventContext(any(), any())).thenReturn(eventContext);
    }

    @Test
    void createScenario_shouldReturnShowEventsScenario_whenShowEventsSignalWasProvided() {
        when(eventScenarioHelper.isShowEventsSignal(request)).thenReturn(true);

        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);
        Scenario scenario = factory.createScenario(request, user);

        assertEquals(ShowEventsListScenario.class, scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnShowLastTransactionsScenario_whenShowLastTransactionsSignalWasProvided() {
        when(billScenarioHelper.isShowLastTransactionsSignal(request)).thenReturn(true);

        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);
        Scenario scenario = factory.createScenario(request, user);

        assertEquals(ShowLastTransactionsScenario.class, scenario.getClass());

    }

    @Test
    void createScenario_shouldThrowScenarioNotFoundException_whenShenarioWasNotFouns() {
        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);

        Exception expectedException = null;

        try {
            factory.createScenario(request, user);
        } catch (ScenarioNotFoundException e) {
            expectedException = e;
        }

        assertNotNull(expectedException);
    }

}