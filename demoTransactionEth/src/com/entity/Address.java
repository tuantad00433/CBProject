package com.entity;

import com.entity.Enum.CurrencyType;
import com.entity.POJO.Balance;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.util.WalletUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    @Unindex
    private Balance balance;

    public Address(String accountId) {
        id = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();
        HashMap<String, String> walletData = WalletUtil.createWalletFile(password);
        wallet = walletData.get("wallet");
        address = "0x"+ walletData.get("walletAddress");
        name = "default address";
        this.accountId = accountId;
        network = "ethereum";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path += accountId + "/address/" + this.id;
        balance = new Balance();
    }

    public Address(String accountId, String name) {
        this.name = name;
        this.accountId = accountId;
        id = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();
        HashMap<String, String> walletData = WalletUtil.createWalletFile(password);
        wallet = walletData.get("wallet");
        address ="0x"+ walletData.get("walletAddress");
        network = "ethereum";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
        resource_path += accountId + "/address/" + this.id;
        balance = new Balance();
    }

    public Address() {
        id = UUID.randomUUID().toString();
        name = "default address";
        network = "etherium";
        created_at = Time.getTimeUTC();
        updated_at = Time.getTimeUTC();
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
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

    public Balance getLastestBalance() {
        Balance balance = new Balance();
        try {
            Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/4ANomGN5hGuPbpCZEIoj"));
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            EthGetBalance ethGetBalance = web3j.ethGetBalance(this.address, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger intObj = ethGetBalance.getBalance();
            BigDecimal decimalObj = new BigDecimal(intObj);
            double amount = decimalObj.doubleValue();
            balance.setCurrency(CurrencyType.ETH.toString());
            balance.setAmount(amount);
            return balance;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return balance;
        }
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
