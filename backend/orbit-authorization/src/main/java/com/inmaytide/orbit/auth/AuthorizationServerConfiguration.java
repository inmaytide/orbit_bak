package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.client.CaptchaClient;
import com.inmaytide.orbit.auth.exception.DefaultWebResponseExceptionTranslator;
import com.inmaytide.orbit.auth.interceptor.CaptchaInterceptor;
import com.inmaytide.orbit.commons.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CaptchaClient captchaClient;

    @Value("${validate.captcha:true}")
    private boolean validateCaptcha;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("apps")
                .authorizedGrantTypes("password", "refresh_token")
                .secret("{noop}" + Constants.SIGNING_KEY);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        if (validateCaptcha) {
            endpoints.addInterceptor(new CaptchaInterceptor(captchaClient));
        }
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .exceptionTranslator(new DefaultWebResponseExceptionTranslator());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constants.SIGNING_KEY);
        return converter;
    }

}