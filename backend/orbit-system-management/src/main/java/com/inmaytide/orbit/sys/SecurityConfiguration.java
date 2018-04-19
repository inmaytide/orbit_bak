package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.security.commons.SecurityConfigurerAdapter;
import com.inmaytide.orbit.util.AccessTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.match;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                .addFilterAt(oauth2Filter(exceptionHandler), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(exceptionTranslationWebFilter(exceptionHandler), SecurityWebFiltersOrder.EXCEPTION_TRANSLATION)
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .matchers(exchange -> AccessTokenUtils.matchInnerToken(exchange) ? match() : notMatch()).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
