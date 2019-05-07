package com.inmaytide.orbit.uaa.auth;

import com.inmaytide.orbit.commons.exception.BadCredentialsException;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@FrameworkEndpoint
public class AdditionalEndpoint {

    @Autowired
    private KeyPair keyPair;

    @Autowired
    private UserService userService;

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @GetMapping("/authenticated")
    @ResponseBody
    public User getAuthenticatedUser() {
        return userService.getAuthenticatedUser().map(this::ensurePassword).orElseThrow(BadCredentialsException::new);
    }

    private User ensurePassword(User user) {
        user.setPassword(null);
        return user;
    }

}
