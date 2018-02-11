package com.inmaytide.orbit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Component
@ConfigurationProperties(prefix = "orbit.cors")
public class CorsProperties {

    private String mapping = "/**";

    private Boolean allowCredentials = true;

    private String allowedOrigins = "*";

    private String allowedMethods = "POST,GET,DELETE,PUT,PATCH";

    private String allowedHeaders = "*";

    private Long maxAge = 3600L;

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(String allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(String allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public CorsConfiguration translate() {
        CorsConfiguration config = new CorsConfiguration();
        config.setMaxAge(getMaxAge());
        config.setAllowCredentials(getAllowCredentials());
        String[] headers = getAllowedHeaders().split(",");
        Arrays.stream(headers).map(String::trim).forEach(config::addAllowedHeader);
        String[] methods = getAllowedMethods().split(",");
        Arrays.stream(methods).map(String::trim).forEach(config::addAllowedMethod);
        String[] origins = getAllowedOrigins().split(",");
        Arrays.stream(origins).map(String::trim).forEach(config::addAllowedOrigin);
        return config;
    }
}
