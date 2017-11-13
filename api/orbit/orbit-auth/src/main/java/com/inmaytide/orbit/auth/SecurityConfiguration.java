package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.provider.FormAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.reactive.WebFluxSecurityConfiguration;
import org.springframework.security.web.server.WebFilterChainFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration extends WebFluxSecurityConfiguration {

    @Autowired
    private FormAuthenticationProvider customizeAuthenticationProvider;

    @Autowired
    private ObjectPostProcessor objectPostProcessor;

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().anyRequest()
//                .and().authorizeRequests().antMatchers("/oauth/**").permitAll();
//    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor).authenticationProvider(customizeAuthenticationProvider).build();
    }


    @Override
    public WebFilterChainFilter springSecurityWebFilterChainFilter() {
        return super.springSecurityWebFilterChainFilter();
    }
}