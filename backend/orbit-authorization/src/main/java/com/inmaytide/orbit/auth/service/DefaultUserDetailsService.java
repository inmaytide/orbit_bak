package com.inmaytide.orbit.auth.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.AuthorizationClient;
import com.inmaytide.orbit.commons.exception.auth.DisabledUserException;
import com.inmaytide.orbit.commons.exception.auth.UnexpectedUsernameException;
import com.inmaytide.orbit.commons.util.Assert;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthorizationClient client;

    private Set<GrantedAuthority> getAuthorities(String username) {
        System.out.println(client.listPermissions(username));
        return client.listPermissions(username).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "username cannot be empty");
        ObjectNode user;
        try {
            user = client.getUser(username);
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new UnexpectedUsernameException();
            }
            throw e;
        }
        Assert.isTrue(!user.get("disabled").asBoolean(), DisabledUserException::new);
        return User.withUsername(username)
                .password(user.get("password").asText())
                .accountLocked(false)
                .authorities(getAuthorities(username))
                .build();
    }

}
