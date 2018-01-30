package com.inmaytide.orbit.sys.filter;

import com.inmaytide.orbit.filter.visitor.AbstractVisitorResolver;
import com.inmaytide.orbit.filter.visitor.UserProvider;
import com.inmaytide.orbit.sys.domain.User;

import java.io.Serializable;
import java.util.Optional;

public class VisitorResolver extends AbstractVisitorResolver<User> {

    public VisitorResolver(UserProvider<User> provider) {
        super(provider);
    }

    public static Optional<User> currentVisitor() {
        Serializable value = visitor.get();
        if (value == null) {
            throw new IllegalStateException("Unauthorized access, please access the system through normal channels.");
        }
        return Optional.of((User) value);
    }

}
