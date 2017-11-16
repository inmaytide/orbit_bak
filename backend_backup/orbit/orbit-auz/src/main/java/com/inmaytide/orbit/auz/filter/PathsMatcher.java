package com.inmaytide.orbit.auz.filter;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Moss
 * @since October 19, 2017
 */
public class PathsMatcher {

    private static final Logger log = LoggerFactory.getLogger(PathsMatcher.class);

    private static final String NOT_MATCH = "NOT_MATCH";

    @Value("#{ @environment['server.root-path'] ?: '/' }")
    private String rootPath;

    private final PatternMatcher patternMatcher;

    private AuthenticatingFilterDefinitions filterDefinitions;

    public PathsMatcher(AuthenticatingFilterDefinitions filterDefinitions) {
        this.filterDefinitions = filterDefinitions;
        this.patternMatcher = new AntPathMatcher();
    }


    public String getMatchFilter(ServerHttpRequest request) {
        return filterDefinitions
                .getPatterns()
                .filter(pattern -> pathsMatch(pattern, request))
                .findFirst()
                .map(filterDefinitions::getDefinition)
                .orElse(NOT_MATCH);
    }

    protected boolean pathsMatch(String pattern, ServerHttpRequest request) {
        String requestURI = request.getPath().pathWithinApplication().value();
        requestURI = requestURI.replace(getRootPath(), "");
        log.trace("Attempting to match pattern '{}' with current requestURI '{}'...", pattern, requestURI);
        return pathsMatch(pattern, requestURI);
    }

    protected boolean pathsMatch(String pattern, String requestURI) {
        return patternMatcher.matches(pattern, requestURI);
    }

    public String getRootPath() {
        return rootPath;
    }

}
