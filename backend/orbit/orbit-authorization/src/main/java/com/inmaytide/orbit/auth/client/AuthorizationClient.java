package com.inmaytide.orbit.auth.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.stream.Stream;

@FeignClient("orbit-system-managment")
public interface AuthorizationClient {

    @GetMapping("/users/{username}")
    Optional<ObjectNode> getUser(@PathVariable("username") String username);

    @GetMapping("/permissions/codes/{username}")
    Stream<String> getPermissionCodes(@PathVariable("username") String username);

    @GetMapping("/roles/codes/{username}")
    Stream<String> getRoleCodes(@PathVariable("username") String username);

}
