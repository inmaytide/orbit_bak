package com.inmaytide.orbit.commons.query;


import com.inmaytide.orbit.commons.util.Assert;

import java.util.List;
import java.util.Map;

public class Params {
    public static final String PARAMS_PAGE_NUMBER = "pageNumber";
    public static final String PARAMS_PAGE_SIZE = "pageSize";
    public static final List<Object> INVALID_VALUES = List.of("all", "-1", -1);

    private Map<String, Object> content;

    public Params(Map<String, Object> content) {
        this(content, true);
    }

    public Params(Map<String, Object> content, boolean needEraseInvalid) {
        Assert.nonNull(content, "The content of params cannot be null");
        this.content = content;
        if (needEraseInvalid) {
            eraseInvalidParams();
        }
    }

    private void eraseInvalidParams() {
        if (content.isEmpty()) {
            return;
        }
        content.entrySet().stream()
                .filter(entry -> INVALID_VALUES.contains(entry.getValue()))
                .forEach(entry -> content.remove(entry.getKey()));
    }

    public Integer getInt(String name, Integer defaultValue) {
        if (content == null || content.isEmpty()) {
            return defaultValue;
        }
        Object value = content.getOrDefault(name, defaultValue);
        return value == null ? defaultValue : Integer.parseInt(value.toString());
    }

    public Integer getInt(String name) {
        return getInt(name, null);
    }

    public Integer getPageNumber() {
        return getInt(Params.PARAMS_PAGE_NUMBER, 1);
    }

    public Integer getPageSize() {
        return getInt(Params.PARAMS_PAGE_SIZE, Integer.MAX_VALUE);
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void addParam(String name, Object value) {
        content.put(name, value);
    }
}
