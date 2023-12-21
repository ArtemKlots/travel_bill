package com.travelBill.telegram.scenario;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.telegram.driver.ChatType;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.GroupScenarioFactory;
import com.travelBill.telegram.scenario.group.IgnoreMessageScenario;
import com.travelBill.telegram.scenario.individual.IndividualScenarioFactory;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ScenarioFactory {
    public final static String START_MESSAGE = "/start";
    private final IndividualScenarioFactory individualScenarioFactory;
    private final GroupScenarioFactory groupScenarioFactory;
    private final UpdateIndividualKeyboardScenario updateIndividualKeyboardScenario;
    private final UpdateGroupKeyboardScenario updateGroupKeyboardScenario;
    private final InitialScenario initialScenario;
    private final KeyboardVersionStorage keyboardVersionStorage;
    private final EventService eventService;

    public ScenarioFactory(IndividualScenarioFactory individualScenarioFactory,
                           GroupScenarioFactory groupScenarioFactory,
                           UpdateIndividualKeyboardScenario updateIndividualKeyboardScenario,
                           UpdateGroupKeyboardScenario updateGroupKeyboardScenario, InitialScenario initialScenario,
                           KeyboardVersionStorage keyboardVersionStorage,
                           EventService eventService) {
        this.individualScenarioFactory = individualScenarioFactory;
        this.groupScenarioFactory = groupScenarioFactory;
        this.updateIndividualKeyboardScenario = updateIndividualKeyboardScenario;
        this.updateGroupKeyboardScenario = updateGroupKeyboardScenario;
        this.initialScenario = initialScenario;
        this.keyboardVersionStorage = keyboardVersionStorage;
        this.eventService = eventService;
    }

    public Scenario createScenario(Request request) {
        Scenario scenario;
        boolean isPrivateChat = request.chatType == ChatType.PRIVATE;

        if (isStartMessage(request)) {
            scenario = initialScenario;
        } else if (isPrivateChat) {
            scenario = getScenarioForPrivateChat(request);
        } else {
            scenario = getScenarioForGroupChat(request);
        }

        return scenario;
    }

    private Scenario getScenarioForPrivateChat(Request request) {
        Scenario scenario;
        if (isPrivateKeyboardNeedsUpdate(request)) {
            scenario = updateIndividualKeyboardScenario;
        } else {
            scenario = individualScenarioFactory.createScenario(request);
        }
        return scenario;
    }

    private Scenario getScenarioForGroupChat(Request request) {
        Scenario scenario;
        Event event = eventService.findByTelegramChatId(request.chatId);

        if (nonNull(event) && event.getId() == 30) {
            // 23 - VikaShv. 29 - VikaB
            if (request.user.getId() == 23 || request.user.getId() == 29) {
                return new IgnoreMessageScenario();
            }
        }

        if (nonNull(event) && (isNull(event.getLastActivity()) || event.getLastActivity().isBefore(keyboardVersionStorage.getGroupKeyboardReleaseDate()))) {
            scenario = updateGroupKeyboardScenario;
        } else {
            scenario = groupScenarioFactory.createScenario(request);
        }
        return scenario;
    }

    private boolean isStartMessage(Request request) {
        return request.hasMessage() && request.message.equals(START_MESSAGE);
    }

    private boolean isPrivateKeyboardNeedsUpdate(Request request) {
        return isNull(request.user.getLastActivity()) || request.user.getLastActivity().isBefore(keyboardVersionStorage.getPrivateKeyboardReleaseDate());
    }


}
