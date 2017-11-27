package com.inmaytide.orbit;

import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.security.FailedAuthenticationEntryPoint;
import com.inmaytide.orbit.security.OAuth2AuthenticationConverter;
import com.inmaytide.orbit.security.OAuth2AuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authorization.ExceptionTranslationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

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

    @Bean
    public FailedAuthenticationEntryPoint failedAuthenticationEntryPoint() {
        return new FailedAuthenticationEntryPoint();
    }

    public AuthenticationWebFilter oauth2Filter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(new OAuth2AuthenticationManager());
        filter.setAuthenticationConverter(new OAuth2AuthenticationConverter(tokenStore()));
        filter.setServerSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        filter.setServerAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(failedAuthenticationEntryPoint()));
        return filter;
    }

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .exceptionHandling()
                .serverAuthenticationEntryPoint(failedAuthenticationEntryPoint())
                .and()
                .addFilterAt(oauth2Filter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/oauth/**", "/lang/**", "/captcha/**").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
