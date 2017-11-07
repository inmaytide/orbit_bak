package com.inmaytide.orbit.consts;

public enum PermissionCategory {

    MENU("Menu", "MENU"),
    BUTTON("Button", "BUTTON");

    private String name;
    private String code;

    PermissionCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
