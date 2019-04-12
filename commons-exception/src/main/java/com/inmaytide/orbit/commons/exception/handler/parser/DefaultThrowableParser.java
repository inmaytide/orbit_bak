package com.inmaytide.orbit.commons.exception.handler.parser;

import com.inmaytide.orbit.commons.exception.BadRequestException;
import com.inmaytide.orbit.commons.exception.DefaultRuntimeException;
import com.inmaytide.orbit.commons.exception.NotAuthenticatedException;
import com.inmaytide.orbit.commons.exception.PathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultThrowableParser implements ThrowableParser {

    private static final Logger logger = LoggerFactory.getLogger(DefaultThrowableParser.class);

    private static final Map<String, Class<? extends Throwable>> MAPPERS = Map.of(
            "org.springframework.security.authentication.AuthenticationCredentialsNotFoundException",
            NotAuthenticatedException.class
    );

    @Override
    public Throwable parse(Throwable e) {
        if (e instanceof DefaultRuntimeException) {
            return e;
        }

        if (e instanceof IllegalArgumentException) {
            return new BadRequestException(e.getMessage());
        }

        if (MAPPERS.containsKey(e.getClass().getName())) {
            return map(e);
        }

        if (e instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) e;
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                return new PathNotFoundException();
            }
        }

        if (e instanceof WebExchangeBindException) {
            WebExchangeBindException ex = (WebExchangeBindException) e;
            List<FieldError> errors = ex.getFieldErrors();
            String messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
            messages = String.format("Bad parameters（%s）：%s", errors.size(), messages);
            return new BadRequestException(messages);
        }

        logger.error("Unexpected exception", e);

        return e;
    }


    private Throwable map(Throwable e) {
        try {
            return MAPPERS.get(e.getClass().getName()).getConstructor().newInstance();
        } catch (Exception e1) {
            logger.error("Can't parse {} to {}, cause by: ", e.getClass(), MAPPERS.get(e.getClass().getName()));
            logger.error("", e1);
            return e;
        }
    }
}
