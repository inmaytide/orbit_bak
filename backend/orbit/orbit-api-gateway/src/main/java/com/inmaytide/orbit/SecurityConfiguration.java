package com.inmaytide.orbit;

import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.filter.AuthenticatingFilter;
import com.inmaytide.orbit.filter.OAuth2AuthenticationConverter;
import com.inmaytide.orbit.filter.OAuth2AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfiguration {


    @Bean
    public JwtAccessTokenConverter converter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constants.SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }


    public AuthenticationWebFilter oauth2Filter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(new OAuth2AuthenticationManager());
        filter.setAuthenticationConverter(new OAuth2AuthenticationConverter(tokenStore()));
        return filter;
    }


    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .addFilterAt(oauth2Filter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/oauth/**", "/lang/**").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
