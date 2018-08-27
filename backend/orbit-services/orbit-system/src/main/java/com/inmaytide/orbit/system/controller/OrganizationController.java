package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.system.domain.Organization;
import com.inmaytide.orbit.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @PostMapping
    public Mono<Organization> create(@Validated @RequestBody Mono<Organization> organization) {
        return organization.doOnSuccess(this::assertCodeNotExist).map(service::save);
    }


    private void assertCodeNotExist(Organization inst) {
        if (inst.getCode() != null) {
            Assert.isTrue(!service.exist(inst.getCode(), inst.getId() == null ? -1L : inst.getId()), "Code is existed");
        }
    }

}
