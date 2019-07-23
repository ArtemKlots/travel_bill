package com.travelBill.telegram.scenario;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.TelegramUpdateUtils;
import com.travelBill.telegram.scenario.common.scenario.InitialScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.GroupScenarioFactory;
import com.travelBill.telegram.scenario.individual.IndividualScenarioFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

@Service
public class ScenarioFactory {
    private final IndividualScenarioFactory individualScenarioFactory;
    private final GroupScenarioFactory groupScenarioFactory;
    private final TelegramUpdateUtils telegramUpdateUtils;

    public ScenarioFactory(IndividualScenarioFactory individualScenarioFactory, GroupScenarioFactory groupScenarioFactory, TelegramUpdateUtils telegramUpdateUtils) {
        this.individualScenarioFactory = individualScenarioFactory;
        this.groupScenarioFactory = groupScenarioFactory;
        this.telegramUpdateUtils = telegramUpdateUtils;
    }

    public Scenario createScenario(Update update, User currentUser) {
        Scenario scenario;
        boolean isUserChat = telegramUpdateUtils.getChat(update).isUserChat();

        if (InitialScenarioHelper.isStart(update)) {
            scenario = new InitialScenario(update);
        } else if (isUserChat) {
            scenario = individualScenarioFactory.createScenario(update, currentUser);
        } else {
            scenario = groupScenarioFactory.createScenario(update, currentUser);
        }

        if (isNull(scenario)) {
            scenario = new UnknownScenario(update);
        }

        return scenario;
    }


}
