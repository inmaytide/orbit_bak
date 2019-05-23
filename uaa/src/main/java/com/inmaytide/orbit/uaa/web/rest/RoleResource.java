package com.inmaytide.orbit.uaa.web.rest;

import com.inmaytide.orbit.uaa.domain.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/roles")
public class RoleResource {

    @PostMapping
    @PreAuthorize("hasPermission('PERMISSIONS_ADD')")
    public Role create(@RequestBody Role role) {
        return role;
    }

}
