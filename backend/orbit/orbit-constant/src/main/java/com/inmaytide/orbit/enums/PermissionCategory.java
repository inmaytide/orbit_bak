package com.inmaytide.orbit.enums;

import java.util.Objects;
import java.util.stream.Stream;

public enum PermissionCategory {

    MENU(10001L, "Menu"),
    BUTTON(10002L, "Button");

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

    public static PermissionCategory valueOf(Long code) {
        return Stream.of(values()).filter(category -> Objects.equals(code, category.getCode()))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
