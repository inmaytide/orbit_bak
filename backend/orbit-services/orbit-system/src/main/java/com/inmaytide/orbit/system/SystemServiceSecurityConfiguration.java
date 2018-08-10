package com.inmaytide.orbit.system;

import com.inmaytide.orbit.commons.security.SecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SystemServiceSecurityConfiguration extends SecurityConfigurerAdapter {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return httpSecurityConfiguer(http)
                .authorizeExchange().pathMatchers("/menus/*").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
