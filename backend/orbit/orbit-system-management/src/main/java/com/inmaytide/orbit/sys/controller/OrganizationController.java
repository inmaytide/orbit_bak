package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.domain.Organization;
import com.inmaytide.orbit.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Moss
 * @since December 27, 2017
 */
@RequestMapping("sys/organizations")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationService service;

    @GetMapping
    public Flux<Organization> list(Long parent) {
        Assert.notNull(parent, "Parameter parent must not be null");
        return Flux.fromIterable(service.listByParent(parent));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Organization> add(@RequestBody @Valid Organization inst, BindingResult bind) {
        processBindingResult(bind);
        return Mono.just(service.insert(inst));
    }

}
