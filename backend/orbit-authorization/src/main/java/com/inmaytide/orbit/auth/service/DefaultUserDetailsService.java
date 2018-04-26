package com.inmaytide.orbit.auth.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.AuthorizationClient;
import com.inmaytide.orbit.commons.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthorizationClient client;

    private Set<GrantedAuthority> getAuthorities(String username) {
        return Stream.concat(client.getPermissionCodes(username).stream(), client.getRoleCodes(username).stream().map(authority -> "ROLE_" + authority))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "username cannot be empty");
        ObjectNode user = client.getUser(username);
        Assert.nonNull(user, () -> new UsernameNotFoundException("Unknown account"));
        Assert.isTrue(!user.get("locked").asBoolean(), () -> new LockedException("Locked account"));
        return User.withUsername(username)
                .password(user.get("password").asText())
                .accountLocked(false)
                .authorities(getAuthorities(username))
                .build();
    }

}
