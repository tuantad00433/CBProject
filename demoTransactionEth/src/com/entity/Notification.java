package com.entity;

import com.entity.POJO.Balance;
import com.entity.POJO.Pagination;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.util.RESTUtil;

import java.util.HashMap;
import java.util.UUID;

@Entity
public class Notification {
    @Id
    private String id;
    @Index
    private String type;
    @Index
    private HashMap<String, Object> data;
    @Index
    private HashMap<String, Object> user;
    @Index
    private HashMap<String, Object> account;
    @Index
    private String created_at;
    @Index
    private String updated_at;
    @Index
    private final String resource = "notification";
    @Index
    private String resource_path;
    private HashMap<String, Object> subscriber;
    private HashMap<String, Object> delivery_response;
    private int delivery_attempts;

    public Notification() {
        id = UUID.randomUUID().toString();
        resource_path = "/v2/notifications/"+id;
    }
    public Notification(String userId, String accountId) {
        id = UUID.randomUUID().toString();
        resource_path = "/v2/notifications/"+id;
        user.put("id",userId);
        account.put("id",accountId);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, Object> getUser() {
        return user;
    }

    public void setUser(HashMap<String, Object> user) {
        this.user = user;
    }

    public HashMap<String, Object> getAccount() {
        return account;
    }

    public void setAccount(HashMap<String, Object> account) {
        this.account = account;
    }

    public int getDelivery_attempts() {
        return delivery_attempts;
    }

    public void setDelivery_attempts(int delivery_attempts) {
        this.delivery_attempts = delivery_attempts;
    }

    public HashMap<String, Object> getDelivery_response() {
        return delivery_response;
    }

    public void setDelivery_response(HashMap<String, Object> delivery_response) {
        this.delivery_response = delivery_response;
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

    public HashMap<String, Object> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(HashMap<String, Object> subscriber) {
        this.subscriber = subscriber;
    }

}
