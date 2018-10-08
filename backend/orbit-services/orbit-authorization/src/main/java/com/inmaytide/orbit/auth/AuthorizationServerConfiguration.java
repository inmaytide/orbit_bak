package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.client.CaptchaClient;
import com.inmaytide.orbit.auth.exception.DefaultWebResponseExceptionTranslator;
import com.inmaytide.orbit.auth.interceptor.CaptchaInterceptor;
import com.inmaytide.orbit.auth.service.DefaultUserDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String KEY_PUBLIC_EXPONENT = "65537";
    private static final String KEY_PRIVATE_EXPONEND = "38797549732099772238261983728151163552446426601955692799425992845017037660609722182769207885768915665688213926467740323269169315590250284155302904219780453028699474811225614727742922525605376946973219278636004704457386414363346164763149826537661395300963958176965093232725094897297838803610835521488263295329";
    private static final String KEY_MODULES = "92032540060540856130699856218033980227909420161154272440856424391337831083081633223257042754076930142688883599931819080863310751260975563655931896404073841267772979089007431866988369523052660671922204391891685962100812539714008553578527942925270385160702076827458753644833661843321703086079889898175420563511";

    private AuthenticationManager authenticationManager;

    @Autowired
    private CaptchaClient captchaClient;

    @Autowired
    private DefaultUserDetailsService service;

    @Value("${validate.captcha:true}")
    private boolean validateCaptcha;

    public AuthorizationServerConfiguration(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("apps")
                .authorizedGrantTypes("password", "refresh_token")
                .secret("{noop}59a84cbf83227a35");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (validateCaptcha) {
            endpoints.addInterceptor(new CaptchaInterceptor(captchaClient));
        }
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(service)
                .exceptionTranslator(new DefaultWebResponseExceptionTranslator());
    }

    @Bean
    public TokenStore tokenStore() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws InvalidKeySpecException, NoSuchAlgorithmException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(KEY_MODULES), new BigInteger(KEY_PUBLIC_EXPONENT));
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(KEY_MODULES), new BigInteger(KEY_PRIVATE_EXPONEND));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return new KeyPair(factory.generatePublic(publicKeySpec), factory.generatePrivate(privateKeySpec));
    }

}

@FrameworkEndpoint
class JwkSetEndpoint {

    @Autowired
    private KeyPair keyPair;

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}