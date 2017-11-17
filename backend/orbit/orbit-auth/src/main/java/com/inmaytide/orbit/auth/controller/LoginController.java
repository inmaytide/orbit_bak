package com.inmaytide.orbit.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @RequestMapping(value = "success", method = {RequestMethod.GET, RequestMethod.POST})
    public Object success() {
        Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
        return authenication;
    }

}
