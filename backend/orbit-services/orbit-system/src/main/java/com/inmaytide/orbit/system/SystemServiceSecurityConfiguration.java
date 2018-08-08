package com.inmaytide.orbit.system;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.security.SecurityConfigurerAdapter;
import com.inmaytide.orbit.commons.security.matcher.InsideTokenMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SystemServiceSecurityConfiguration extends SecurityConfigurerAdapter {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(exceptionHandler::handle)
                .accessDeniedHandler(exceptionHandler::handle)
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(jwtFilter(exceptionHandler), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .matchers(exchange -> new InsideTokenMatcher(exchange).match()).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
