package com.inmaytide.orbit.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Moss
 * @since November 21, 2017
 */
@RestController
public class UserProvider {

    @GetMapping("oauth/user")
    public Object user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
