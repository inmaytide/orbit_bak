package com.inmaytide.orbit.handler.sys;

import com.inmaytide.orbit.domain.sys.Permission;
import com.inmaytide.orbit.handler.AbstractHandler;
import com.inmaytide.orbit.service.sys.PermissionService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

/**
 * @author Moss
 * @since October 11, 2017
 */
@Component
public class PermissionHandler extends AbstractHandler {

    @Resource
    private PermissionService service;

    private Permission validate(Permission permission) {
        Assert.isTrue(super.validate(permission)
                        || service.checkCode(permission.getCode(), permission.getId()),
                "The code cannot not be repeat");
        return permission;
    }

    @Nonnull
    public Mono<ServerResponse> list(ServerRequest request) {
        return ok().body(Flux.fromIterable(service.listNodes(request.queryParam("category"))), Permission.class);
    }

    @Nonnull
    public Mono<ServerResponse> add(ServerRequest request) {
        return request
                .bodyToMono(Permission.class)
                .map(this::validate)
                .map(service::insert)
                .transform(mono -> status(CREATED).body(mono, Permission.class));
    }

    @Nonnull
    public Mono<ServerResponse> update(ServerRequest request) {
        return request
                .bodyToMono(Permission.class)
                .map(this::idRequireNonnull)
                .map(this::validate)
                .map(service::update)
                .transform(permissionMono -> ok().body(permissionMono, Permission.class));
    }

    @Nonnull
    public Mono<ServerResponse> remove(ServerRequest request) {
        String ids = request.pathVariable("ids");
        requireNotBlank(ids);
        service.remove(ids);
        return status(NO_CONTENT).build();
    }

    @Nonnull
    public Mono<ServerResponse> checkCode(ServerRequest request) {
        Long id = NumberUtils.toLong(request.pathVariable("id"));
        String code = request.pathVariable("code");
        return ok().body(Mono.just(service.checkCode(code, id)), Boolean.class);
    }

    @Nonnull
    public Mono<ServerResponse> move(ServerRequest request) {
        Long id = NumberUtils.toLong(request.pathVariable("id"));
        String category = request.pathVariable("category");
        service.move(id, category);
        return status(NO_CONTENT).build();
    }
}
