package com.travelBill.telegram.scenario;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.ChatType;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.GroupScenarioFactory;
import com.travelBill.telegram.scenario.individual.IndividualScenarioFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.travelBill.telegram.scenario.ScenarioFactory.START_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScenarioFactoryTest {
    private Request request;
    private User currentUserMock = mock(User.class);
    private IndividualScenarioFactory individualScenarioFactoryMock = mock(IndividualScenarioFactory.class);
    private GroupScenarioFactory groupScenarioFactoryMock = mock(GroupScenarioFactory.class);
    private ScenarioFactory scenarioFactory = new ScenarioFactory(individualScenarioFactoryMock, groupScenarioFactoryMock);
    private Scenario scenarioMock = mock(Scenario.class);

    @BeforeEach
    void setupContext() {
        request = new Request();
    }

    @Test
    void createScenario_shouldReturnInitialScenario_WhenStartMessageReceived() {
        request.message = START_MESSAGE;

        Scenario result = scenarioFactory.createScenario(request, currentUserMock);
        assertEquals(InitialScenario.class, result.getClass());
    }

    @Test
    void createScenario_shouldReturnResultOfGroupScenarioFactory_WhenMessageIsNull() {
        when(groupScenarioFactoryMock.createScenario(request, currentUserMock)).thenReturn(scenarioMock);
        Scenario result = scenarioFactory.createScenario(request, currentUserMock);
        assertEquals(scenarioMock, result);
    }

    @Test
    void createScenario_shouldReturnResultOfIndividualScenarioFactory_WhenChatIsPrivate() {
        request.chatType = ChatType.PRIVATE;
        when(individualScenarioFactoryMock.createScenario(request, currentUserMock)).thenReturn(scenarioMock);
        Scenario result = scenarioFactory.createScenario(request, currentUserMock);
        assertEquals(scenarioMock, result);
    }

    @Test
    void createScenario_shouldReturnResultOfGroupScenarioFactory_WhenChatIsGroup() {
        request.chatType = ChatType.GROUP;
        when(groupScenarioFactoryMock.createScenario(request, currentUserMock)).thenReturn(scenarioMock);
        Scenario result = scenarioFactory.createScenario(request, currentUserMock);
        assertEquals(scenarioMock, result);
    }

    @Test
    void createScenario_shouldReturnResultOfGroupScenarioFactory_WhenChatIsSuperGroup() {
        request.chatType = ChatType.SUPER_GROUP;
        when(groupScenarioFactoryMock.createScenario(request, currentUserMock)).thenReturn(scenarioMock);
        Scenario result = scenarioFactory.createScenario(request, currentUserMock);
        assertEquals(scenarioMock, result);
    }
}
