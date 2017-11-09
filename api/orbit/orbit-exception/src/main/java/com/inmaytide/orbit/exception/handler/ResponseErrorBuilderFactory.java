package com.inmaytide.orbit.exception.handler;

import com.inmaytide.orbit.exception.InvalidTokenException;
import com.inmaytide.orbit.exception.PathNotFoundException;
import com.inmaytide.orbit.exception.VersionMatchedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ResponseErrorBuilderFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseErrorBuilderFactory.class);

    public static ResponseErrorBuilder get(Throwable e) {
        ResponseErrorBuilders builder = ResponseErrorBuilders.valueOf(e);
        if (builder == ResponseErrorBuilders.Default) {
            String message = String.format("Unexpected error: [%s => %s]", e.getClass().getSimpleName(), e.getMessage());
            log.error(message, e);
        } else {
            log.error("{} => {}", e.getClass().getName(), e.getMessage());
        }
        return builder.getBuilder();
    }

    private enum ResponseErrorBuilders {

        NotFound(PathNotFoundException.class, e -> ResponseError.of(HttpStatus.NOT_FOUND, e.getMessage())),
        //Authentication(AuthenticationException.class, e -> ResponseError.of(HttpStatus.FORBIDDEN, getLoginErrorMessage(e.getClass()))),
        //Unauthorized(UnauthorizedException.class, e -> ResponseError.of(HttpStatus.FORBIDDEN, e.getMessage())),
        //Unauthenticated(UnauthenticatedException.class, e -> ResponseError.of(HttpStatus.UNAUTHORIZED, "Not logged in")),
        InvalidToken(InvalidTokenException.class, e -> ResponseError.of(HttpStatus.UNAUTHORIZED, "Failed to login with token, because the token is invalidated, maybe it were expire")),
        IllegalArgument(IllegalArgumentException.class, e -> ResponseError.of(HttpStatus.BAD_REQUEST, e.getMessage())),
        VersionMatched(VersionMatchedException.class, e -> ResponseError.of(HttpStatus.CONFLICT, "The data has updated or not exist, please try again after refresh the page")),
        Default(Exception.class, e -> ResponseError.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName() + "=>" + e.getMessage()));

        static Map<Class<? extends Throwable>, String> LOGIN_ERROR_MESSAGE_MAPPER;

        static {
            LOGIN_ERROR_MESSAGE_MAPPER = new ConcurrentHashMap<>();
//            LOGIN_ERROR_MESSAGE_MAPPER.put(AccountException.class, "login.error.username.empty");
//            LOGIN_ERROR_MESSAGE_MAPPER.put(UnknownAccountException.class, "login.error.username.wrong");
//            LOGIN_ERROR_MESSAGE_MAPPER.put(LockedAccountException.class, "login.error.user.locked");
//            LOGIN_ERROR_MESSAGE_MAPPER.put(IncorrectCaptchaException.class, "login.error.captcha.wrong");
//            LOGIN_ERROR_MESSAGE_MAPPER.put(IncorrectCredentialsException.class, "login.error.password.wrong");
        }

        private Class<? extends Throwable> exceptionClass;

        private ResponseErrorBuilder builder;

        ResponseErrorBuilders(Class<? extends Throwable> exceptionClass, ResponseErrorBuilder builder) {
            this.exceptionClass = exceptionClass;
            this.builder = builder;
        }

        public static ResponseErrorBuilders valueOf(Throwable e) {
            return Stream.of(values())
                    .filter(builder -> builder.getExceptionClass().isInstance(e))
                    .findFirst().orElse(Default);
        }

//        private static String getLoginErrorMessage(Class<? extends Throwable> cls) {
//            String key = LOGIN_ERROR_MESSAGE_MAPPER.getOrDefault(cls, "login.error");
//            return I18nMessages.getInstance().get(key, key);
//        }

        public Class<? extends Throwable> getExceptionClass() {
            return exceptionClass;
        }

        public ResponseErrorBuilder getBuilder() {
            return builder;
        }
    }

}

