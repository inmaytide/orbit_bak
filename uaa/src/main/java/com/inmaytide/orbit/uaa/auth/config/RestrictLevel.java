package com.inmaytide.orbit.uaa.auth.config;

public enum RestrictLevel {

    NORMAL(2),
    CAPTCHE(5),
    FORBIDDEN_5(8),
    FORBIDDEN_10(10),
    FORBIDDEN_15(15);

    private Integer upper;

    RestrictLevel(Integer upper) {
        this.upper = upper;
    }

    public Integer getUpper() {
        return upper;
    }
}
