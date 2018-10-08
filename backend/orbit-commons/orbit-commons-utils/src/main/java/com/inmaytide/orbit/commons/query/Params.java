package com.inmaytide.orbit.commons.query;


import com.inmaytide.orbit.commons.util.Assert;

import java.util.*;

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
        this.content = needEraseInvalid ? eraseInvalidParams(content) : content;
    }

    private Map<String, Object> eraseInvalidParams(Map<String, Object> params) {
        final Map<String, Object> erased = new HashMap<>();
        if (!params.isEmpty()) {
            params.entrySet().stream()
                    .filter(entry -> hasText(entry.getValue()) && !INVALID_VALUES.contains(entry.getValue()))
                    .forEach(entry -> erased.put(entry.getKey(), entry.getValue()));
        }
        return Collections.unmodifiableMap(erased);
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

    private boolean hasText(Object value) {
        if (value == null) {
            return false;
        }
        return value.toString().trim().length() != 0;
    }
}
