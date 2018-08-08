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

import java.util.Set;

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

    @GetMapping("/u/{username}/permissions")
    public Mono<Set<String>> listPermissions(@PathVariable String username) {
        return Mono.just(service.listPermissions(username));
    }

}
