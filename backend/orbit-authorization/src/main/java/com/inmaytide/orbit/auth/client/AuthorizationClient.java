package com.inmaytide.orbit.auth.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient("orbit-system")
public interface AuthorizationClient {

    @Cacheable(value = "users::profile", key = "#p0")
    @GetMapping("/users/u/{username}")
    Map<String, Object> getUser(@PathVariable("username") String username);

    @Cacheable(value = "users::permissions", key = "#p0")
    @GetMapping("/users/u/{username}/permissions")
    List<String> listPermissions(@PathVariable("username") String username);
}
