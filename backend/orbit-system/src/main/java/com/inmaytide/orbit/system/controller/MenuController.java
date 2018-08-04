package com.inmaytide.orbit.system.controller;

import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping("/own")
    public Flux<Menu> listByUsername(@AuthenticationPrincipal Principal principal) {
        return Flux.fromIterable(service.listByUsername(principal.getName()));
    }

}
