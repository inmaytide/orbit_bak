package com.inmaytide.orbit.auth;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.AuthorizationClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

public class AdditionalAccessTokenConverter extends DefaultAccessTokenConverter {

    private AuthorizationClient client;

    AdditionalAccessTokenConverter(AuthorizationClient authorizationClient) {
        this.client = authorizationClient;
    }

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);
        ObjectNode user = client.getUser(authentication.getName());
        if (user != null) {
            response.put("name", user.get("name").asText());
            response.put("avatar", user.get("avatar").asText());
        }
        return response;
    }
}
