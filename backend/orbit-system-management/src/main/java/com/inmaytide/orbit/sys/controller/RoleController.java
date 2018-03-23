package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("sys/roles")
public class RoleController {

    @Resource
    private RoleService service;

    @GetMapping("codes/{username}")
    public Mono<Set<String>> listCodesByUsername(@PathVariable String username) {
        return Mono.just(service.listCodesByUsername(username));
    }

}
