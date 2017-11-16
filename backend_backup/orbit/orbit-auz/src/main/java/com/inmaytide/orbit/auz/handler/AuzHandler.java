package com.inmaytide.orbit.auz.handler;

import com.inmaytide.orbit.auz.util.SessionUtils;
import com.inmaytide.orbit.auz.provider.CaptchaProvider;
import com.inmaytide.orbit.auz.provider.PermissionProvider;
import com.inmaytide.orbit.auz.realm.JwtRealm;
import com.inmaytide.orbit.auz.token.FormAuthenticationToken;
import com.inmaytide.orbit.domain.sys.Permission;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.log.LogAnnotation;
import com.inmaytide.orbit.util.CommonUtils;
import com.inmaytide.orbit.auz.util.TokenUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromResource;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 04, 2017
 */
@Component
public class AuzHandler {

    private static final Logger log = LoggerFactory.getLogger(AuzHandler.class);

    @Autowired
    private CaptchaProvider captchaProvider;

    @Autowired
    private PermissionProvider permissionProvider;

    @Autowired
    private JwtRealm jwtRealm;

    @Autowired
    private AuzHandler instance;

    public RouterFunction<?> routers() {
        return route(POST("/login").and(accept(APPLICATION_JSON)), instance::login)
                .and(route(GET("/captcha"), instance::captcha))
                .and(route(GET("/user/menus"), instance::getMenusOfSomeone));
    }

    @LogAnnotation(value = "系统登录", success = "登录成功", failure = "登录失败")
    public User login(FormAuthenticationToken token) {
        SessionUtils.getSubject().login(token);
        return SessionUtils.getCurrentUser().map(user -> {
            user.setToken(TokenUtils.generate(CommonUtils.uuid(), user.getUsername()));
            jwtRealm.getAuthenticationCache().remove(token.getUsername());
            return user;
        }).orElseThrow(AuthenticationException::new);
    }

    @Nonnull
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(FormAuthenticationToken.class)
                .map(instance::login)
                .transform(mono -> ok().body(mono, User.class));
    }

    @Nonnull
    public Mono<ServerResponse> captcha(ServerRequest request) {
        final String keySuffix = request.queryParam("v").orElse("");
        final Resource resource = captchaProvider.generateCaptcha("png", keySuffix);
        return ok().contentType(MediaType.IMAGE_PNG).body(fromResource(resource));
    }

    @Nonnull
    public Mono<ServerResponse> getMenusOfSomeone(ServerRequest request) {
        User user = SessionUtils.getCurrentUser().orElseThrow(UnauthenticatedException::new);
        Flux<Permission> flux = Flux.fromIterable(permissionProvider.listMenusByUsername(user.getUsername()));
        return ok().body(flux, Permission.class);
    }

}
