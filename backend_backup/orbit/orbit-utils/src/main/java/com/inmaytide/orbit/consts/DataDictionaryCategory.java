package com.inmaytide.orbit.consts;

/**
 * @author Moss
 * @since November 07, 2017
 */
public enum DataDictionaryCategory {

    PERMISSION_CATEGORY("permission.category");

    private String value;

    DataDictionaryCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
