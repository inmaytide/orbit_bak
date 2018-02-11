package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.sys.domain.Permission;
import com.inmaytide.orbit.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("sys/permissions")
public class PermissionController extends AbstractController {

    @Autowired
    private PermissionService service;

    @GetMapping("codes/{username}")
    public Mono<Set<String>> listCodesByUsername(@PathVariable String username) {
        return Mono.just(service.listCodesByUsername(username));
    }

    @GetMapping("/{username}")
    public Flux<Permission> listByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listMenusByUsername(username));
    }

    @GetMapping
    public Flux<Permission> list(Long category) {
        List<Permission> list = Objects.isNull(category) ? service.listNodes() : service.listNodes(category);
        return Flux.fromIterable(list);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Permission> add(@RequestBody @Valid Permission permission, BindingResult bind) {
        processBindingResult(bind);
        Assert.isTrue(service.checkCode(permission.getCode(), permission.getId()), "The permission code cannot not be repeat");
        return Mono.just(service.insert(permission));
    }

    @PutMapping
    public Mono<Permission> update(@RequestBody @Valid Permission permission, BindingResult bind) {
        processBindingResult(bind);
        Assert.notNull(permission.getId(), "The primary key of instance must not be null when it will be update");
        Assert.isTrue(service.checkCode(permission.getCode(), permission.getId()), "The permission code cannot not be repeat");
        return Mono.just(service.update(permission));
    }

    @DeleteMapping("/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String ids) {
        service.remove(ids);
    }

    @GetMapping("/checkCode/{id}/{code}")
    public Mono<Map<String, Object>> checkCode(@PathVariable Long id, @PathVariable String code) {
        return Mono.just(Map.of("isRepeat", service.checkCode(code, id)));
    }

    @PatchMapping("/move/{id}/{category}")
    public void move(@PathVariable Long id, @PathVariable String category) {
        this.service.move(id, category);
    }

}
