package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.provider.CustomizeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomizeAuthenticationProvider customizeAuthenticationProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
                .and().authorizeRequests().antMatchers("/oauth/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customizeAuthenticationProvider);
    }
}