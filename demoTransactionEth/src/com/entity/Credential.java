package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.UUID;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
public class Credential {
    @Id
    private String tokenKey;
    @Unindex
    private String secretToken;
    @Index
    private String userWalletId;
    @Index
    private long createdTime;
    @Index
    private long expiredTime;
    @Index
    private int status;//0-expired;1-active

    public Credential(String userWalletId) {
        this.userWalletId = userWalletId;
        this.tokenKey = UUID.randomUUID().toString();
        this.secretToken = tokenKey;
        this.createdTime = System.currentTimeMillis();
        this.expiredTime = this.createdTime + 86400 * 1000;
        this.status = 1;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
        this.secretToken = tokenKey;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getUserWalletId() {
        return userWalletId;
    }

    public void setUserWalletId(String userWalletId) {
        this.userWalletId = userWalletId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Credential loadCredential(String secretToken) {
        if (secretToken == null) {
            return null;
        }
        Credential credential = ofy().load().type(Credential.class).id(secretToken).now();
        if (credential.getExpiredTime() < System.currentTimeMillis()) {
            return null;
        }
        return credential;
    }
}
