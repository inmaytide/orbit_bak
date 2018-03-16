package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.domain.Organization;
import com.inmaytide.orbit.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Moss
 * @since December 27, 2017
 */
@RestController
@RequestMapping("sys/organizations")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationService service;

    @GetMapping
    public Flux<Organization> list(Long parent) {
        return Flux.fromIterable(service.listByParent(parent));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Organization> add(@RequestBody @Valid Mono<Organization> organization) {
        return organization.onErrorResume(Mono::error).doOnSuccess(service::insert);
    }

}
