package com.travelBill.telegram.scenario.group.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

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
        String stringBillId = request.callbackQueryData.split("_")[BILL_ID_INDEX_IN_CALLBACK];
        Long billId = Long.parseLong(stringBillId);
        Bill billToRemove = billService.findById(billId, request.user.getId());

        billService.delete(billToRemove, request.user);
        botApi.deleteMessage(request.chatId, request.messageId);

        DeleteBillResponseBuilder responseBuilder = new DeleteBillResponseBuilder();
        responseBuilder.bill = billToRemove;
        responseBuilder.memberFullName = request.user.getFullName();

        return responseBuilder.build();
    }

}
