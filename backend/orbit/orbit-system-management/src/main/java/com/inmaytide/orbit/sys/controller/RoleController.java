package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("codes/{username}")
    public Flux<String> listCodesByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listCodesByUsername(username));
    }

}
