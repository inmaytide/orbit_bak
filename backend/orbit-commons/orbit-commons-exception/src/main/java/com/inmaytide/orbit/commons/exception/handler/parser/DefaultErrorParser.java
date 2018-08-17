package com.inmaytide.orbit.commons.exception.handler.parser;

import com.inmaytide.orbit.commons.exception.PathNotFoundException;
import com.inmaytide.orbit.commons.exception.auth.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultErrorParser implements ErrorParser {

    private static final Logger logger = LoggerFactory.getLogger(DefaultErrorParser.class);
    private static final Map<String, Class<? extends Throwable>> MAPPERS = Map.of(
            "org.springframework.security.authentication.AuthenticationCredentialsNotFoundException",
            NotAuthenticatedException.class
    );


    @Override
    public Throwable parse(Throwable e) {
        if (MAPPERS.containsKey(e.getClass().getName())) {
            return mapped(e);
        }

        if (e instanceof WebExchangeBindException) {
            WebExchangeBindException ex = (WebExchangeBindException) e;
            List<FieldError> errors = ex.getFieldErrors();
            String messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
            messages = String.format("Bad parameters（%s）：%s", errors.size(), messages);
            return new IllegalArgumentException(messages);
        }

        if (e instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) e;
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                return new PathNotFoundException();
            }
        }
        return e;
    }

    private Throwable mapped(Throwable e) {
        try {
            return MAPPERS.get(e.getClass().getName()).getConstructor().newInstance();
        } catch (Exception e1) {
            logger.error("parse error", e1);
            return e;
        }
    }
}
