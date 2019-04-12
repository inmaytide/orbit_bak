module commons.exception {
    requires reactor.core;
    requires org.reactivestreams;
    requires spring.web;
    requires spring.core;
    requires spring.webflux;
    requires spring.context;
    requires slf4j.api;

    exports com.inmaytide.orbit.commons.exception;
    exports com.inmaytide.orbit.commons.exception.handler;
    exports com.inmaytide.orbit.commons.exception.handler.parser;
}