package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.telegram.driver.Response;

public class JoinEventSuccessResponseBuilder extends JoinEventResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = getMessage();
        return response;
    }

    public String getMessage() {
        return String.format(
                "Hello %s! Now I know that you are here and can receive your contributions :)",
                member.getFullName());
    }

}
