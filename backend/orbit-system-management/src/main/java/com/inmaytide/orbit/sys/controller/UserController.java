package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sys/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        Assert.notNull(service, "UserService must not be null");
        this.service = service;
    }

    @GetMapping("/{username}")
    public Mono<User> getByUsername(@PathVariable String username) {
        return Mono.justOrEmpty(service.getByUsername(username));
    }

}
