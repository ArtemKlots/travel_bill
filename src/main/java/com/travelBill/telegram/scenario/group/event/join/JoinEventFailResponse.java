package com.travelBill.telegram.scenario.group.event.join;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class JoinEventFailResponse extends JoinEventResponse {

    @Override
    public String getMessage() {
        return String.format("Hmm... It seems I saw you in this chat. You are %s, right?", member.getFullName());
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
