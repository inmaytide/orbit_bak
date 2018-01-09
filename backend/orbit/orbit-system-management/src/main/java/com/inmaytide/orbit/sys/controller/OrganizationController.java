package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.domain.Organization;
import com.inmaytide.orbit.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Moss
 * @since December 27, 2017
 */
@RequestMapping("sys/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @GetMapping
    public Flux<Organization> list(Long parent) {
        return Flux.fromIterable(service.listByParent(parent));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Organization> add(Organization inst) {
        return Mono.just(service.insert(inst));
    }

}
