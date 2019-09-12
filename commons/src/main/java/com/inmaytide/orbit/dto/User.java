package com.inmaytide.orbit.dto;

import com.inmaytide.orbit.enums.UserStatus;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private UserStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean disabled() {
        return status == UserStatus.DISABLED;
    }
}
