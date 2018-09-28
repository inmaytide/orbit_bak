package com.inmaytide.orbit.commons.security;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.security.matcher.InsideTokenMatcher;
import com.inmaytide.orbit.commons.util.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public abstract class SecurityConfigurerAdapter implements InitializingBean {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    protected ServerHttpSecurity httpSecurityConfiguer(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(getExceptionHandler()::handle)
                .accessDeniedHandler(getExceptionHandler()::handle)
                .and()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .matchers(exchange -> new InsideTokenMatcher(exchange).match()).permitAll()
                .and();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return httpSecurityConfiguer(http).build();
    }


    protected GlobalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.nonNull(exceptionHandler, "Exception handler for security must not be null");
    }
}
