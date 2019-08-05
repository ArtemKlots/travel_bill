package com.travelBill.telegram.scenario;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.ChatType;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.GroupScenarioFactory;
import com.travelBill.telegram.scenario.individual.IndividualScenarioFactory;
import org.springframework.stereotype.Service;

@Service
public class ScenarioFactory {
    public final static String START_MESSAGE = "/start";
    private final IndividualScenarioFactory individualScenarioFactory;
    private final GroupScenarioFactory groupScenarioFactory;

    public ScenarioFactory(IndividualScenarioFactory individualScenarioFactory, GroupScenarioFactory groupScenarioFactory) {
        this.individualScenarioFactory = individualScenarioFactory;
        this.groupScenarioFactory = groupScenarioFactory;
    }

    public Scenario createScenario(Request request, User currentUser) {
        Scenario scenario;
        boolean isPrivateChat = request.chatType == ChatType.PRIVATE;

        if (request.hasMessage() && request.message.equals(START_MESSAGE)) {
            scenario = new InitialScenario(request);
        } else if (isPrivateChat) {
            scenario = individualScenarioFactory.createScenario(request, currentUser);
        } else {
            scenario = groupScenarioFactory.createScenario(request, currentUser);
        }

        return scenario;
    }


}
