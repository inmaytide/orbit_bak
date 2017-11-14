package com.inmaytide.orbit.auth.controller;

import com.inmaytide.orbit.auth.token.FormAuthenticatedToken;
import com.inmaytide.orbit.domain.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Moss
 * @since November 13, 2017
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public User login(FormAuthenticatedToken token) {
        token = (FormAuthenticatedToken) authenticationManager.authenticate(token);
        return token.getUser();
    }

}
