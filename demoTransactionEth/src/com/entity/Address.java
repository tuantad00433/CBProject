package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.util.WalletUtil;

import java.util.HashMap;
import java.util.UUID;

@Entity
public class Address {
    @Id
    private String id;
    @Index
    private String address;
    @Index
    private String name;
    @Index
    private String network;
    @Index
    private String created_at;
    @Index
    private String updated_at;
    @Index
    private final String resource = "address";
    @Index
    private String resource_path = "/v2/accounts/";
    @Index
    private String accountId;
    @Unindex
    private String wallet;
    @Unindex
    private String password;

    public Address(String accountId) {
        id = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();
        HashMap<String,String> walletData = WalletUtil.createWalletFile(password);
        wallet = walletData.get("wallet");
        address = walletData.get("walletAddress");
        name = "default address";
        this.accountId = accountId;
        network = "ethereum";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path += accountId + "/address/" + this.id;

    }

    public Address(String accountId, String name) {
        this.name = name;
        this.accountId = accountId;
        id = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();
        HashMap<String,String> walletData = WalletUtil.createWalletFile(password);
        wallet = walletData.get("wallet");
        address = walletData.get("walletAddress");
        network = "ethereum";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path += accountId + "/address/" + this.id;
    }

    public Address() {
        id = UUID.randomUUID().toString();
        name = "default address";
        network = "etherium";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.id == null || this.id.length() == 0) {
            errors.put("id", "id of address is not well-form");
        }
        if (this.accountId == null || this.accountId.length() == 0) {
            errors.put("accountId", "accountId cannot be empty or null");
        }
        return errors;
    }
}
