package com.inmaytide.orbit.commons.exception.handler.parser;

@FunctionalInterface
public interface ThrowableParser {

    Throwable parse(Throwable e);

}
