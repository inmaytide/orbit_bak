package com.inmaytide.orbit.enums;

public enum PermissionCategory {

    MENU("Menu"),
    BUTTON("Button");

    private String text;

    PermissionCategory(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
