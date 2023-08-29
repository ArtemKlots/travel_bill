package com.travelBill.api.core.bill.statistic;

import com.travelBill.api.core.user.User;

import java.util.List;

public class UserSpentStatisticItem {
    public User user;
    public List<CurrencyStatisticItem> statistic;

    public UserSpentStatisticItem(User user, List<CurrencyStatisticItem> statistic) {
        this.user = user;
        this.statistic = statistic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CurrencyStatisticItem> getStatistic() {
        return statistic;
    }

    public void setStatistic(List<CurrencyStatisticItem> statistic) {
        this.statistic = statistic;
    }
}
