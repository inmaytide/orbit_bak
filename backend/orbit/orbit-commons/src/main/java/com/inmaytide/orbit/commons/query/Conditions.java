package com.inmaytide.orbit.commons.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Conditions implements Serializable {

    private static final long serialVersionUID = 9218848259837884151L;

    private Map<String, Object> conditions;

    public Conditions() {
        this.conditions = new HashMap<>();
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
