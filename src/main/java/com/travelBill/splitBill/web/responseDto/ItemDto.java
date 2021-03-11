package com.travelBill.splitBill.web.responseDto;


import java.util.ArrayList;
import java.util.List;

public class ItemDto {

    private Long id;

    private String title;

    private double amount;

    private double price;

    private Long billId;

    private List<AssignDto> assigns;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public List<AssignDto> getAssigns() {
        if (assigns == null) return new ArrayList<>();
        return assigns;
    }

    public void setAssigns(List<AssignDto> assigns) {
        this.assigns = assigns;
    }
}
