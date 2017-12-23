package com.inmaytide.orbit.exception.handler;

import com.inmaytide.orbit.exception.PathNotFoundException;
import com.inmaytide.orbit.exception.VersionMatchedException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpStatusParser {

    private final static Map<Class<? extends Throwable>, HttpStatus> CLASS_MAPPERS;

    private final static Map<String, HttpStatus> CLASS_NAME_MAPPERS;

    static {
        CLASS_MAPPERS = new ConcurrentHashMap<>();
        CLASS_MAPPERS.put(PathNotFoundException.class, HttpStatus.NOT_FOUND);
        CLASS_MAPPERS.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        CLASS_MAPPERS.put(IllegalAccessException.class, HttpStatus.FORBIDDEN);
        CLASS_MAPPERS.put(VersionMatchedException.class, HttpStatus.CONFLICT);

        CLASS_NAME_MAPPERS = new ConcurrentHashMap<>();
        CLASS_NAME_MAPPERS.put("org.springframework.security.core.AuthenticationException", HttpStatus.FORBIDDEN);
        CLASS_NAME_MAPPERS.put("org.springframework.security.authentication.AuthenticationCredentialsNotFoundException", HttpStatus.FORBIDDEN);
        CLASS_NAME_MAPPERS.put("org.springframework.orm.ObjectOptimisticLockingFailureException", HttpStatus.CONFLICT);
        CLASS_NAME_MAPPERS.put("org.springframework.web.bind.support.WebExchangeBindException", HttpStatus.BAD_REQUEST);
        CLASS_NAME_MAPPERS.put("com.inmaytide.orbit.auth.exception.BadCaptchaException", HttpStatus.FORBIDDEN);
    }

    public static HttpStatus parser(Throwable e) {
        return CLASS_MAPPERS.keySet().stream()
                .filter(cls -> cls.isInstance(e))
                .findFirst().map(CLASS_MAPPERS::get)
                .orElse(parser(e.getClass().getName()));
    }

    private static HttpStatus parser(String throwableClassName) {
        return CLASS_NAME_MAPPERS.getOrDefault(throwableClassName, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
