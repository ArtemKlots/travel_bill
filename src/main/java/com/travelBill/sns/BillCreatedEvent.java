package com.travelBill.sns;

public class BillCreatedEvent {
    private Long chatId;
    private Integer messageId;
    private Long billId;
    private Long eventId;
    private Long userId;
    private String billPurpose;

    public BillCreatedEvent(Long chatId, Integer messageId, Long billId, Long eventId, Long userId, String billPurpose) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.billId = billId;
        this.eventId = eventId;
        this.userId = userId;
        this.billPurpose = billPurpose;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBillPurpose() {
        return billPurpose;
    }

    public void setBillPurpose(String billPurpose) {
        this.billPurpose = billPurpose;
    }
}
