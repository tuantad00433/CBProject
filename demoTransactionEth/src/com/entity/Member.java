package com.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.HashMap;
import java.util.UUID;

@Entity
public class Member {
    @Id
    private String email;
    @Index
    private String firstName;
    @Index
    private String lastName;
    @Index
    private String password;
    @Index
    private String userId;
    @Index
    private int status;

    public Member() {
        status = 1;
        this.userId = UUID.randomUUID().toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();
        if (this.email == null || this.email.length() == 0) {
            errors.put("email", "email is missing or invalid");
        }
        if (this.firstName == null || this.firstName.length() == 0) {
            errors.put("firstName", "firstName is missing or invalid");
        }
        if (this.lastName == null || this.lastName.length() == 0) {
            errors.put("lastName", "lastName is misisng or invalid");
        }
        if (this.password == null || this.password.length() == 0) {
            errors.put("[password", "password is not well-form");
        }
        if (this.userId == null || this.userId.length() == 0) {
            errors.put("userId", "userId is not well-form");
        }

        return errors;
    }


}
