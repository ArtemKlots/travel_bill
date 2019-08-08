package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;

public abstract class JoinEventResponse implements Response {
    public User member;
}
