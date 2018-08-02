package com.inmaytide.orbit.auth.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("orbit-system")
public interface AuthorizationClient {

    @GetMapping("/users/u/{username}")
    ObjectNode getUser(@PathVariable("username") String username);

    @GetMapping("/users/u/{username}/permissions")
    List<String> listPermissions(@PathVariable("username") String username);
}
