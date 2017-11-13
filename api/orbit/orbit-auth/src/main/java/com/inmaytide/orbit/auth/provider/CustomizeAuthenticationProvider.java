package com.inmaytide.orbit.auth.provider;

import com.inmaytide.orbit.auth.token.AuthenticatedToken;
import com.inmaytide.orbit.domain.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class CustomizeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = Objects.toString(authentication.getCredentials(), null);

//        Map<Object, Object> details = (Map<Object, Object>) authentication.getDetails();
//        String clientId = Objects.toString(details.get("client"), null);
//        Assert.hasText(clientId, "The client value must be not empty");

        User user = getUserByUsername(username);

        System.out.println(user);

        return new AuthenticatedToken(user);
    }

    @Override
    public boolean supports(Class<?> cls) {
        return true;
    }

    private User getUserByUsername(String username) {
        return restTemplate.getForObject("http://orbit-system-management/users/" + username, User.class);
    }
}
