package com.inmaytide.orbit.exception.handler;

@FunctionalInterface
public interface ResponseErrorBuilder {

    ResponseError build(Throwable e);

}