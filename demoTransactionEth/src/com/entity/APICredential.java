package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.UUID;

@Entity
public class APICredential {
    @Id
    private String APIKey;
    @Index
    private String APISecretKey;
    @Index
    private String userId;
    @Index
    private long createdTime;

    @Index
    private int status;

    public APICredential(String userId) {
        this.APIKey = UUID.randomUUID().toString();
        this.APISecretKey = UUID.randomUUID().toString() +  String.valueOf(Math.random());
        this.userId = userId;
        this.createdTime = System.currentTimeMillis();

        this.status = 1;
    }
    public APICredential(){

    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String getAPISecretKey() {
        return APISecretKey;
    }

    public void setAPISecretKey(String APISecretKey) {
        this.APISecretKey = APISecretKey;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
