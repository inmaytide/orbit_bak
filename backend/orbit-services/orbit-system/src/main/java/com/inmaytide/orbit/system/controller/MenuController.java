package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Moss
 * @since August 04, 2018
 */
@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping("/u/{username}")
    public Flux<Menu> listByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listByUsername(username));
    }

    @GetMapping
    public Flux<Menu> list() {
        return Flux.fromIterable(service.all());
    }

    @GetMapping("/{id}")
    public Mono<Menu> get(@PathVariable Long id) {
        return service.get(id).map(Mono::just).orElseThrow(ObjectNotFoundException::new);
    }

    @GetMapping("/exist")
    public Mono<Map<String, Boolean>> exist(String code, Long ignore) {
        return Mono.just(Map.of("isValid", service.exist(code, ignore)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Menu> insert(@RequestBody @Validated Mono<Menu> menu) {
        return menu.doOnSuccess(this::assertCodeNotExist).map(service::save);
    }

    @PutMapping
    public Mono<Menu> update(@RequestBody @Validated Mono<Menu> menu) {
        return menu.doOnSuccess(this::assertCodeNotExist).map(service::update);
    }

    @PatchMapping
    public Mono<Menu> updateSelective(@RequestBody Mono<Menu> menu) {
        return menu.doOnSuccess(this::assertCodeNotExist).map(service::updateSelective);
    }


    private void assertCodeNotExist(Menu menu) {
        if (menu.getCode() != null) {
            Assert.isTrue(!service.exist(menu.getCode(), menu.getId() == null ? -1L : menu.getId()), "Code is existed");
        }
    }

}
