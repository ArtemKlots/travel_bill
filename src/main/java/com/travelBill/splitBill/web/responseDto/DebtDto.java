package com.travelBill.splitBill.web.responseDto;

public class DebtDto {
    private double amount;
    private Long debtorId;
    private String debtorFullName;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public String getDebtorFullName() {
        return debtorFullName;
    }

    public void setDebtorFullName(String debtorFullName) {
        this.debtorFullName = debtorFullName;
    }
}
