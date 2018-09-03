package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.system.domain.Organization;
import com.inmaytide.orbit.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @GetMapping
    public Flux<Organization> list() {
        return Flux.fromIterable(service.all());
    }

    @PostMapping
    public Mono<Organization> create(@Validated @RequestBody Mono<Organization> organization) {
        return organization.doOnSuccess(this::assertCodeNotExist).map(service::save);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping("/exist")
    public Mono<Map<String, Boolean>> exist(String code, Long ignore) {
        return Mono.just(Map.of("exist", service.exist(code, ignore)));
    }

    private void assertCodeNotExist(Organization inst) {
        if (inst.getCode() != null) {
            Assert.isTrue(!service.exist(inst.getCode(), inst.getId() == null ? -1L : inst.getId()), "Code is existed");
        }
    }

}
