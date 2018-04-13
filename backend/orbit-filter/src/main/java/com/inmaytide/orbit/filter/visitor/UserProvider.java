package com.inmaytide.orbit.filter.visitor;

import java.io.Serializable;

@FunctionalInterface
public interface UserProvider<T extends Serializable> {

    T getByUsername(String username);

}
