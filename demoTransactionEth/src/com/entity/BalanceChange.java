package com.entity;

import java.util.UUID;

public class BalanceChange {
    private String id;
    private String address;
    private double change;

    public BalanceChange() {
        id = UUID.randomUUID().toString();
    }

    public BalanceChange(String address, double changes) {
        id = UUID.randomUUID().toString();
        this.address = address;
        this.change = changes;
    }
    public static BalanceChange getInstance(){
       return new BalanceChange();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getChange() {
        return change;
    }

    public void setChanges(double change) {
        this.change = change;
    }
}
