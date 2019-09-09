package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.telegram.Response;

public class JoinEventSuccessResponseBuilder extends JoinEventResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = getMessage();
        return response;
    }

    public String getMessage() {
        return String.format(
                "Hello %s! Now I know that you are here and can receive your contributions :) \n\n" +
                        "Use the following pattern to make a contribution (spaces are important!): \n<how much> <currency> <purpose> \n\n" +
                        "Example: 10 points to Gryffindor",
                member.getFullName());
    }

}
