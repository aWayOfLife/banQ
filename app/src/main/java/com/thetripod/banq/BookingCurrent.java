package com.thetripod.banq;


public class BookingCurrent {
    private String userId;
    private String serviceId;
    private String slot;
    private String branch;
    private String bookingTimestamp;
    private String status;
    private String eta;
    private String queuePosition;
    private String bookingId;
    private String bankerId;

    public BookingCurrent() {
    }

    public BookingCurrent(String userId, String serviceId, String slot, String branch, String bookingTimestamp, String status, String eta, String queuePosition, String bookingId, String bankerId) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.slot = slot;
        this.branch = branch;
        this.bookingTimestamp = bookingTimestamp;
        this.status = status;
        this.eta = eta;
        this.queuePosition = queuePosition;
        this.bookingId = bookingId;
        this.bankerId = bankerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    public void setBookingTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(String queuePosition) {
        this.queuePosition = queuePosition;
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
}
