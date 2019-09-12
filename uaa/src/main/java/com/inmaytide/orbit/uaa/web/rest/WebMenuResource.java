package com.inmaytide.orbit.uaa.web.rest;

import com.inmaytide.orbit.uaa.domain.WebMenu;
import com.inmaytide.orbit.uaa.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luomiao
 * @since 2019/09/12
 */
@RequestMapping("/api")
@RestController
public class WebMenuResource {

    @Autowired
    private WebMenuService service;

    @GetMapping("/users/{id}/web-menus")
    public List<WebMenu> getByUser(@PathVariable("id") Long userId) {
        return service.getByUser(userId);
    }

}
