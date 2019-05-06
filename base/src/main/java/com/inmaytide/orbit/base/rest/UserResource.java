package com.inmaytide.orbit.base.rest;

import com.inmaytide.orbit.base.domain.User;
import com.inmaytide.orbit.base.service.UserService;
import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping("/u/{username}")
    public User getByUsername(@PathVariable String username) {
        return service.getByUsername(username)
                .orElseThrow(ObjectNotFoundException::new);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.get(id).orElseThrow(ObjectNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return service.save(user);
    }


}
