package com.inmaytide.orbit.enums;

public enum PermissionCategory {

    MENU(377564822935437312L, "Menu"),
    BUTTON(377564822943825920L, "Button");

    private Long code;
    private String text;

    PermissionCategory(Long code, String text) {
        this.code = code;
        this.text = text;
    }

    public Long getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
