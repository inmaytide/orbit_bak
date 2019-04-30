package com.inmaytide.orbit.base.rest;

import com.inmaytide.orbit.base.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @GetMapping("/u/{username}")
    public Mono<User> getByUsername(@PathVariable String username) {
        return Mono.just(new User());
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable Integer id) {
        return Mono.just(new User());
    }


}
