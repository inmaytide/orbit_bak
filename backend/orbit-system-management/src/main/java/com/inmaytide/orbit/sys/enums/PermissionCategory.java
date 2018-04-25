package com.inmaytide.orbit.sys.enums;

public enum PermissionCategory {

    MENU("Menu"),
    FUNCTION("Function");

    private String text;

    PermissionCategory(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
