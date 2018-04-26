package com.inmaytide.orbit.sys.enums;

public enum OrganizationCategory {

    CLIENT("Client"),
    COMPANY("Company"),
    FACTORY("Factory"),
    DEPARTMENT("Department"),
    GROUP("Group");

    private String text;

    OrganizationCategory(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
