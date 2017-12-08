package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.util.WalletUtil;

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
    private AccountBalance balance;
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
    private String wallet;
    @Unindex
    private String password;

    public Account() {

    }

    public Account(String userId) {
        this.userId = userId;
        id = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();
        wallet = WalletUtil.createWalletFile(password);
        name = "My Wallet";
        currency = CurrencyType.ETH;
        primary = false;
        type = AccountType.WALLET;
        balance = new AccountBalance();
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path = "/v2/accounts/" + id;
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

    public AccountBalance getBalance() {
        return balance;
    }

    public void setBalance(AccountBalance balance) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.id == null || this.id.length() == 0) {
            errors.put("id", "ID is not well-form");
        }
        return errors;
    }
}
