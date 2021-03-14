package com.travelBill.splitBill.web.responseDto;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.DebtSumDto;

import java.util.List;

public class DetailedBillDto {

    private Long id;

    private String title;

    private String currency;

    private List<UserDto> members;

    List<ItemDto> items;

    List<DebtDto> debts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<UserDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserDto> members) {
        this.members = members;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public List<DebtDto> getDebts() {
        return debts;
    }

    public void setDebts(List<DebtDto> debts) {
        this.debts = debts;
    }
}
