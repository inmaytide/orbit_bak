package com.inmaytide.orbit.filter.visitor;

import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;

@FunctionalInterface
public interface UserProvider<T extends Serializable> {

    @Cacheable(value = "visitor-json", unless = "#result == null")
    T getByUsername(String username);

}
