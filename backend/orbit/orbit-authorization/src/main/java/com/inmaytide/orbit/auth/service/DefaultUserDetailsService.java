package com.inmaytide.orbit.auth.service;

import com.inmaytide.orbit.auth.client.AuthorizationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthorizationClient client;

    private Set<GrantedAuthority> getAuthorities(String username) {
        return Stream.concat(client.getPermissionCodes(username), client.getRoleCodes(username).map(authority -> "ROLE_" + authority))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "username cannot be empty");
        return client.getUser(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.get("username").asText())
                        .password(user.get("password").asText())
                        .accountLocked(user.get("locked").asBoolean())
                        .authorities(getAuthorities(username))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
