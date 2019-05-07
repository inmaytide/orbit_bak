package com.inmaytide.orbit.uaa.auth.service;

import com.inmaytide.orbit.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getByUsername(username)
                .map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).disabled(u.disabled()).authorities(Collections.emptyList()).build())
                .orElseThrow(() -> new UsernameNotFoundException("Wrong username"));

    }

}
