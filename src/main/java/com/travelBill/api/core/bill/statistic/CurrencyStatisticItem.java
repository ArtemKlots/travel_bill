package com.travelBill.api.core.bill.statistic;

public class CurrencyStatisticItem {
    public String currency;
    public Double amount;

    public CurrencyStatisticItem(String currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
