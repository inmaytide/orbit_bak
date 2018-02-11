package com.inmaytide.orbit.dictionary.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("orbit-system-managment")
public interface UserClient {

    @GetMapping("/users/{username}")
    Optional<ObjectNode> getUserByUsername(@PathVariable("username") String username);

}
