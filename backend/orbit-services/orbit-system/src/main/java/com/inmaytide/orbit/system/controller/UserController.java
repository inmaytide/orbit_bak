package com.inmaytide.orbit.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.inmaytide.orbit.commons.query.Params;
import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody @Validated Mono<User> user) {
        return user.doOnSuccess(service::assertNotExist).map(service::save);
    }

    @PutMapping
    public Mono<User> update(@RequestBody @Validated Mono<User> user) {
        return user.doOnSuccess(service::assertNotExist).map(service::update);
    }

    @PatchMapping("change-password")
    public void changePassword(Mono<User> user) {
        user.doOnSuccess(service::changePassword);
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable Long id) {
        return service.get(id).map(Mono::just).orElseThrow(ObjectNotFoundException::new);
    }

    @GetMapping("/u/{username}/permissions")
    public Mono<Set<String>> listPermissions(@PathVariable String username) {
        return Mono.just(service.listPermissions(username));
    }

    @GetMapping
    public Mono<PageInfo<User>> list(@RequestParam Map<String, Object> params) {
        return Mono.just(service.list(new Params(params)));
    }


}
