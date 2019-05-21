package com.inmaytide.orbit.uaa.auth;

import com.inmaytide.orbit.commons.exception.HttpResponseException;
import com.inmaytide.orbit.commons.exception.LoginRestrictedException;
import com.inmaytide.orbit.commons.exception.handler.parser.DefaultThrowableParser;
import com.inmaytide.orbit.commons.exception.handler.parser.ThrowableParser;
import com.inmaytide.orbit.uaa.utils.RestrictUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultWebResponseExceptionTranslator extends org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator {

    private final HttpResponseExceptionTranslator httpResponseExceptionTranslator;

    public DefaultWebResponseExceptionTranslator() {
        this(new DefaultThrowableParser());
    }

    public DefaultWebResponseExceptionTranslator(ThrowableParser parser) {
        this.httpResponseExceptionTranslator = new HttpResponseExceptionTranslator(parser);
    }

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if (!(e instanceof LoginRestrictedException)) {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            RestrictUtil.increment(request);
        }
        return httpResponseExceptionTranslator.translate(e).orElse(super.translate(e));
    }

    private class HttpResponseExceptionTranslator {

        private final ThrowableParser parser;

        private HttpResponseExceptionTranslator(ThrowableParser parser) {
            this.parser = parser;
        }

        private Optional<ResponseEntity<OAuth2Exception>> translate(Throwable e) {
            return Optional.of(parser.parse(e))
                    .flatMap(this::translateHttpResponseException)
                    .or(() -> e.getCause() != null ? translate(e.getCause()) : Optional.empty());
        }

        private Optional<ResponseEntity<OAuth2Exception>> translateHttpResponseException(Throwable e) {
            return Optional.of(e)
                    .filter(o -> o instanceof HttpResponseException)
                    .map(o -> (HttpResponseException) e)
                    .map(o -> new ResponseEntity<>(new OAuth2Exception(o.getCode(), o), o.getStatus()));
        }

    }
}



