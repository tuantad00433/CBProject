package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.UUID;

@Entity
public class MemberCredential {
    @Id
    private String userId;
    @Index
    private String tokenKey;
    @Index
    private String secretToken;
    @Index
    private long createdTime;
    @Index
    private long expiredTime;
    @Index
    private int status;

    public MemberCredential() {

    }

    public MemberCredential(String userId) {
        tokenKey = UUID.randomUUID().toString();
        secretToken = tokenKey;
        this.userId = userId;
        createdTime = System.currentTimeMillis();
        expiredTime = createdTime + 3600 * 1000;
        status = 1;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public static MemberCredential loadCredential(String secretToken) {
        if (secretToken == null) {
            return null;
        }
        MemberCredential credential = ofy().load().type(MemberCredential.class).filter("tokenKey", secretToken).first().now();
        if (credential == null) {
            return null;
        }
        if (credential.getExpiredTime() < System.currentTimeMillis()) {
            credential.status = 0;
            ofy().save().entity(credential).now();
            return null;
        }
        return credential;
    }
}
