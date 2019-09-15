package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.ChatType;
import com.travelBill.telegram.driver.Request;
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

    public Scenario createScenario(Request request) {
        Scenario scenario;
        boolean isPrivateChat = request.chatType == ChatType.PRIVATE;

        if (request.hasMessage() && request.message.equals(START_MESSAGE)) {
            scenario = new InitialScenario();
        } else if (isPrivateChat) {
            scenario = individualScenarioFactory.createScenario(request);
        } else {
            scenario = groupScenarioFactory.createScenario(request);
        }

        return scenario;
    }


}
