package com.entity;

import com.entity.Enum.AccountType;
import com.entity.Enum.CurrencyType;
import com.entity.POJO.Balance;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.HashMap;
import java.util.UUID;

@Entity
public class Account {
    @Id
    private String id;
    @Index
    private String name;
    @Index
    private boolean primary;
    @Index
    private String currency; //1-BTC;2-ETH;3-LTC.
    @Index
    private String type;
    @Index
    private Balance balance;
    @Index
    private String created_at;
    @Index
    private String updated_at;
    @Index
    private final String resource = "account";
    @Index
    private String resource_path;
    @Index
    private String userId;
    @Index
    private String URLNotification;


    public Account() {

    }

    public Account(String userId,String URLNotification) {
        this.userId = userId;
        id = UUID.randomUUID().toString();
        name = "My Wallet";
        currency = CurrencyType.ETH.toString();
        primary = false;
        type = AccountType.WALLET.toString();
        balance = new Balance();
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path = "/v2/accounts/" + id;
        this.URLNotification = URLNotification;
    }

    public String getURLNotification() {
        return URLNotification;
    }

    public void setURLNotification(String URLNotification) {
        this.URLNotification = URLNotification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getResource() {
        return resource;
    }

    public String getResource_path() {
        return resource_path;
    }

    public void setResource_path(String resource_path) {
        this.resource_path = resource_path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.id == null || this.id.length() == 0) {
            errors.put("id", "ID is not well-form");
        }
        return errors;
    }
}
