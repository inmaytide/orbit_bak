package com.inmaytide.orbit.filter.visitor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.util.JsonUtils;

import java.util.Optional;

public class DefaultVisitorResolver extends AbstractVisitorResolver<String> {

    public DefaultVisitorResolver(UserProvider<String> provider) {
        super(provider);
    }

    public static Optional<ObjectNode> currentVisitor() {
        return Optional.ofNullable(visitor.get()).map(Object::toString).map(JsonUtils::deserialize);
    }

}
