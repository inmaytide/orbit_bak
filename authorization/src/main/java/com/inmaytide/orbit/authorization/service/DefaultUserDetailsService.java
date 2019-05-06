package com.inmaytide.orbit.authorization.service;

import com.inmaytide.orbit.authorization.service.external.UserService;
import com.inmaytide.orbit.dto.UserDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            UserDto user = userService.getUserByUsername(username);
            return User.withUsername(username)
                    .password(user.getPassword())
                    .accountLocked(user.disabled())
                    .authorities(Collections.emptyList())
                    .build();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new UsernameNotFoundException(username);
            }
            throw e;
        }

    }

}
