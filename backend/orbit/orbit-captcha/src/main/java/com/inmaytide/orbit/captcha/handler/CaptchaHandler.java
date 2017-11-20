package com.inmaytide.orbit.captcha.handler;

import com.inmaytide.orbit.captcha.service.CaptchaService;
import com.inmaytide.orbit.commons.consts.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CaptchaHandler {

    @Autowired
    private CaptchaService service;

    private String getCacheName(ServerRequest request) {
        List<String> values = request.headers().header(Constants.HEADER_NAME_SESSION_ID);
        if (!CollectionUtils.isEmpty(values)) {
            return values.get(0);
        } else {
            return request.cookies().getFirst("JSESSIONID").getValue();
        }
    }

    public Mono<ServerResponse> getCaptcha(ServerRequest request) {
        Resource resource = service.generateCaptcha(getCacheName(request));
        return ok().contentType(MediaType.IMAGE_PNG).body(BodyInserters.fromResource(resource));
    }

    public Mono<ServerResponse> validation(ServerRequest request) {
        String captcha = request.pathVariable("captcha");
        Boolean isValid = service.validation(captcha, getCacheName(request));
        return ok().body(Mono.just(isValid), Boolean.class);
    }

}
