package com.inmaytide.orbit.commons.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class RequestConditions implements Serializable {

    private static final long serialVersionUID = 9218848259837884151L;

    private Map<String, Object> conditions;

    private final ServerRequest request;

    public RequestConditions(ServerRequest request) {
        this.conditions = new HashMap<>();
        this.request = request;
    }

    public Optional<String> getParameter(String name) {
        return request.queryParam(name);
    }

    public void fromRequest(String key) {
        getParameter(key).ifPresent(value -> put(key, value));
    }

    public void fromRequest(String key, Function<String, String> translator) {
        Assert.notNull(translator, "The value translator cannot be null");
        getParameter(key).ifPresent(value -> {
            if (StringUtils.isNotBlank(value)) {
                put(key, translator.apply(value));
            }
        });
    }

    public void put(String key, Object value) {
        conditions.put(key, value);
    }

    public Object get(String key) {
        return conditions.getOrDefault(key, null);
    }

    public Map<String, Object> getConditions() {
        return this.conditions;
    }

    @Override
    public String toString() {
        return getConditions().toString();
    }
}
