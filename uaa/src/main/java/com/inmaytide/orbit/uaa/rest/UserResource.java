package com.inmaytide.orbit.uaa.rest;

import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public User create(@RequestBody User user, @AuthenticationPrincipal Principal principal) {
        return service.create(user);
    }


}
