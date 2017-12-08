package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.HashMap;
import java.util.UUID;

@Entity
public class User {
    @Id
    private String id;
    @Index
    private String name; //public name of user.
    @Index
    private String username; //payment method's native currency.
    @Index
    private String profileLocation;
    @Index
    private String profile_bio;
    @Index
    private String profile_url;
    @Index
    private String avatar_url;
    @Index
    private final String resource = "user";
    @Index
    private final String resource_path = "/v2/user";

    public User(String id) {
        this.id = id;
        this.avatar_url = "default";
    }

    public User() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation) {
        this.profileLocation = profileLocation;
    }

    public String getProfile_bio() {
        return profile_bio;
    }

    public void setProfile_bio(String profile_bio) {
        this.profile_bio = profile_bio;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getResource() {
        return resource;
    }

    public String getResource_path() {
        return resource_path;
    }

    private HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.id == null || this.id.length() == 0) {
            errors.put("id", "id is not well-form");
        }

        return errors;
    }
}
