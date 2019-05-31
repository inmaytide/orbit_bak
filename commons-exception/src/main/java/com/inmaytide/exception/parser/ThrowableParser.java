package com.inmaytide.orbit.commons.exception.parser;

@FunctionalInterface
public interface ThrowableParser {

    Throwable parse(Throwable e);

}
