package com.travelBill.api.core.debt;

public interface DebtSumDto {

    double getAmount();

    String getCurrency();

    Long getDebtorId();

    String getUserFirstName();

    String getUserLastName();
}
