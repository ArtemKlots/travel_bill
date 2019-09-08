package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.telegram.Response;

public class JoinEventFailResponseBuilder extends JoinEventResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = String.format("Hmm... It seems I saw you in this chat. You are %s, right?", member.getFullName());
        return response;
    }
}
