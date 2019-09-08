package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;

public class UnknownScenarioResponseBuider implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Sorry, but I can't understand you :( Could you rephrase please?";
        return response;
    }

}
