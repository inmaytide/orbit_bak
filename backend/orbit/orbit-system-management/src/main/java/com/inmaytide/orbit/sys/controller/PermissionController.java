package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.domain.sys.Permission;
import com.inmaytide.orbit.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("sys/permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @GetMapping("codes/{username}")
    public Flux<String> listCodesByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listCodesByUsername(username));
    }

    @GetMapping("/{username}")
    public Flux<Permission> listByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listMenusByUsername(username));
    }

}
