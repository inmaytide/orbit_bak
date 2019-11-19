package com.inmaytide.orbit.uaa.auth.config;

import com.inmaytide.exception.http.handler.servlet.DefaultExceptionHandler;
import com.inmaytide.orbit.uaa.auth.DefaultWebResponseExceptionTranslator;
import com.inmaytide.orbit.uaa.auth.interceptors.CaptchaInterceptor;
import com.inmaytide.orbit.uaa.auth.interceptors.TooManyFailuresInterceptor;
import com.inmaytide.orbit.uaa.auth.service.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private DefaultUserDetailsService defaultUserDetailsService;

    @Autowired
    private DefaultExceptionHandler exceptionHandler;

    public AuthorizationServerConfiguration(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public KeyPair keyPair() {
        return new KeyStoreKeyFactory(new ClassPathResource("auth.jks"), "59a84cbf83227a35".toCharArray()).getKeyPair("orbit");
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("orbit")
                .authorizedGrantTypes("password", "refresh_token")
                .secret("$2a$10$h52qlrFszuA1sIEun.XDT.7w.Cl3Ih/g2QXeFJyn7jQV.O41pViAe")
                .accessTokenValiditySeconds(5 * 60)
                .refreshTokenValiditySeconds(24 * 60 * 60 * 30);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .authenticationEntryPoint(exceptionHandler::handle)
                .accessDeniedHandler(exceptionHandler::handle);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .addInterceptor(new TooManyFailuresInterceptor())
                .addInterceptor(new CaptchaInterceptor())
                .userDetailsService(defaultUserDetailsService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(new DefaultWebResponseExceptionTranslator())
                .accessTokenConverter(jwtAccessTokenConverter());
    }
}
