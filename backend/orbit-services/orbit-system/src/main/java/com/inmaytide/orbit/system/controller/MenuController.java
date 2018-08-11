package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.commons.exception.ObjectInvalidException;
import com.inmaytide.orbit.commons.exception.ObjectNotFoundException;
import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.service.MenuService;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * @author Moss
 * @since August 04, 2018
 */
@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    private MenuService service;

    @Autowired
    private UserService userService;

    @GetMapping("/u/{username}")
    public Flux<Menu> listByUsername(@PathVariable String username) {
        return Flux.fromIterable(service.listByUsername(username));
    }

    @GetMapping("/{id}")
    public Mono<Menu> get(@PathVariable Long id) {
        return service.get(id).map(Mono::just).orElseThrow(ObjectNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Menu> insert(@RequestBody Mono<Menu> menu, @AuthenticationPrincipal Principal principal) {
        return menu
                .doOnSuccess(inst -> {
                    userService.getByUsername(principal.getName())
                            .ifPresent(user -> inst.setCreator(user.getId()));
                    service.save(inst);
                });
    }

}
