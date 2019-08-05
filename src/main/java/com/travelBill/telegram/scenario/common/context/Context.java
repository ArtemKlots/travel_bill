package com.travelBill.telegram.scenario.common.context;

import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.Request;

public abstract class Context {
    public Request request;
    public BotApi botApi;

    public Long getChatId() {
        return request.chatId;
    }

}
