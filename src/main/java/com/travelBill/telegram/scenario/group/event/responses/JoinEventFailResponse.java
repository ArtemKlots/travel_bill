package com.travelBill.telegram.scenario.group.event.responses;

public class JoinEventFailResponse extends JoinEventResponse {

    @Override
    public String getMessage() {
        return String.format("Hmm... It seems I saw you in this chat. You are %s, right?", member.getFullName());
    }
}
