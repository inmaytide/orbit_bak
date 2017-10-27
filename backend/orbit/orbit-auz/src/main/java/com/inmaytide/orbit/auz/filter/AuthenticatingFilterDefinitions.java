package com.inmaytide.orbit.auz.filter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AuthenticatingFilterDefinitions {

    private Map<String, String> filterChainMap;

    public AuthenticatingFilterDefinitions() {
        this.filterChainMap = new LinkedHashMap<>();
    }

    public void addDefinition(String pattern, String permit) {
        filterChainMap.put(pattern, permit);
    }

    public String getDefinition(String pattern) {
        return getDefinitions().get(pattern);
    }

    public Map<String, String> getDefinitions() {
        return filterChainMap;
    }

    public Stream<String> getPatterns() {
        return filterChainMap.keySet().stream();
    }



}
