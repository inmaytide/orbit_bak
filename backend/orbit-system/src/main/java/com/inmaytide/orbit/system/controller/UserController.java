package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/u/{username}")
    public Mono<User> getByUsername(@PathVariable String username) {
        return Mono.just(service.getByUsername(username).orElseThrow(ObjectNotFoundException::new));
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable Long id) {
        return Mono.just(service.get(id).orElseThrow(ObjectNotFoundException::new));
    }

}
