package com.thetripod.banq;

public class BankerDetails {
    private String bankerId;
    private String name;
    private String contactNumber;
    private String city,branch;

    public BankerDetails() {
    }

    public BankerDetails(String bankerId, String name, String contactNumber, String city, String branch) {
        this.bankerId = bankerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.city = city;
        this.branch = branch;
    }

    public void setBankerId(String bankerId) {
        this.bankerId = bankerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankerId() {
        return bankerId;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getCity() {
        return city;
    }

    public String getBranch() {
        return branch;
    }
}
