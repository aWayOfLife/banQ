package com.thetripod.banq;

public class BookingCompleted {
    private String userId;
    private String bookingId;
    private String bankerId;
    private String serviceId;
    private String slot;
    private String branch;
    private String completionTimestamp;
    private String bookingTimestamp;

    public BookingCompleted() {
    }

    public BookingCompleted(String userId, String bankerId, String bookingId){
        this.bankerId = bankerId;
        this.userId = userId;
        this.bookingId = bookingId;
    }

    public BookingCompleted(String userId, String bookingId, String bankerId, String serviceId, String slot, String branch, String completionTimestamp, String bookingTimestamp) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.bankerId = bankerId;
        this.serviceId = serviceId;
        this.slot = slot;
        this.branch = branch;
        this.completionTimestamp = completionTimestamp;
        this.bookingTimestamp = bookingTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBankerId() {
        return bankerId;
    }

    public void setBankerId(String bankerId) {
        this.bankerId = bankerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompletionTimestamp() {
        return completionTimestamp;
    }

    public void setCompletionTimestamp(String completionTimestamp) {
        this.completionTimestamp = completionTimestamp;
    }

    public String getTimestamp() {
        return bookingTimestamp;
    }

    public void setTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    public void setBookingTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }
}
