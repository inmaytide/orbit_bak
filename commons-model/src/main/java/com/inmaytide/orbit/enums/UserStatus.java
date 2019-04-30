package com.inmaytide.orbit.enums;

public enum UserStatus {
    NORMAL(1),
    ON_HOLIDAY(2),
    ON_BUSINESS(3),
    DISABLED(99);

    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }
}
