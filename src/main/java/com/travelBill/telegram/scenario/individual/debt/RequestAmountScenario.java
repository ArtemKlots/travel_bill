package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.user.state.UserState;
import com.travelBill.telegram.user.state.UserStateService;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;
import static com.travelBill.telegram.user.state.State.SEND_MONEY;

@Service
public class RequestAmountScenario implements Scenario {
    private final UserStateService userStateService;
    private final BotApi botApi;

    public RequestAmountScenario(UserStateService userStateService, BotApi botApi) {
        this.userStateService = userStateService;
        this.botApi = botApi;
    }


    @Override
    public Response execute(Request request) {
        //send_money_to_n
        String userId = request.callbackQueryData.split("_")[3];
        userStateService.change(new UserState(request.user, SEND_MONEY, userId));

        Response response = new Response("Type amount in format <*how much*> <*currency*> <*purpose*> or press cancel to reject");
        response.parseMode = MARKDOWN;
        response.inlineKeyboard = new InlineKeyboard();
        response.inlineKeyboard.addRow(new InlineKeyboardButton().setText("Cancel").setCallbackData("delete_previous_message"));

        botApi.deleteMessage(request.chatId, request.messageId);

        return response;
    }
}
