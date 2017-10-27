package com.inmaytide.orbit.consts;

public enum UserStatus {

    NORMAL(1L, "Normal"),
    LOCKED(2L, "Locked");

    private String name;
    private Long code;

    UserStatus(Long code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Long getCode() {
        return code;
    }
}
