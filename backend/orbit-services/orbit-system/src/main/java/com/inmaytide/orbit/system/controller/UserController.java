package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/u/{username}")
    public Mono<User> getByUsername(@PathVariable String username) {
        return service.getByUsername(username).map(Mono::just).orElseThrow(ObjectNotFoundException::new);
    }

    @PutMapping
    public Mono<User> update(@RequestBody @Validated Mono<User> user) {
        return user.doOnSuccess(service::assertNotExist).map(service::update);
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable Long id) {
        return service.get(id).map(Mono::just).orElseThrow(ObjectNotFoundException::new);
    }

    @GetMapping("/u/{username}/permissions")
    public Mono<Set<String>> listPermissions(@PathVariable String username) {
        return Mono.just(service.listPermissions(username));
    }


}
