package com.inmaytide.orbit.base.domain;

public class User extends AbstractEntity {

    private String username;

    private String password;

    private boolean status;

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
}
