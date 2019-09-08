package com.travelBill.telegram.scenario.individual.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class DeleteBillScenario implements Scenario {
    private final BillService billService;
    private final BotApi botApi;


    private static final int BILL_ID_INDEX_IN_CALLBACK = 2;

    public DeleteBillScenario(BillService billService, BotApi botApi) {
        this.billService = billService;
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        String stringBillId = request.callbackQueryData.split("_")[BILL_ID_INDEX_IN_CALLBACK];
        Long billId = Long.parseLong(stringBillId);
        Bill billToRemove = billService.findById(billId, request.user.getId());

        billService.delete(billToRemove, request.user);
        botApi.sendMessage(request.user.getCurrentEvent().getTelegramChatId(), buildGroupResponse(request, billToRemove));
        botApi.deleteMessage(request.chatId, request.messageId);

        DeleteBillPrivateChatResponseBuilder responseBuilder = new DeleteBillPrivateChatResponseBuilder();
        responseBuilder.bill = billToRemove;

        return responseBuilder.build();
    }

    private Response buildGroupResponse(Request request, Bill billToRemove) {
        DeleteBillGroupChatResponseBuilder builder = new DeleteBillGroupChatResponseBuilder();
        builder.bill = billToRemove;
        builder.memberFullName = request.user.getFullName();
        return builder.build();
    }

}
