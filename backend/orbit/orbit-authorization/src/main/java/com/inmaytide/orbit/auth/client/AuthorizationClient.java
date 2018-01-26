package com.inmaytide.orbit.auth.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.util.Optional;

@FeignClient("orbit-system-management")
public interface AuthorizationClient {

    @GetMapping("/sys/users/{username}")
    Optional<ObjectNode> getUser(@PathVariable("username") String username);

    @GetMapping("/sys/permissions/codes/{username}")
    Flux<String> getPermissionCodes(@PathVariable("username") String username);

    @GetMapping("/sys/roles/codes/{username}")
    Flux<String> getRoleCodes(@PathVariable("username") String username);

}
