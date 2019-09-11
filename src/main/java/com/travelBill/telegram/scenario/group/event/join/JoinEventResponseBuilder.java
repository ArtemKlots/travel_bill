package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.ResponseBuilder;

abstract class JoinEventResponseBuilder implements ResponseBuilder {
    User member;
}
