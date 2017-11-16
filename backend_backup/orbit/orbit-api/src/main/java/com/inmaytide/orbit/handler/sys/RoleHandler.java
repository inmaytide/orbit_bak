package com.inmaytide.orbit.handler.sys;

import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Role;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.handler.AbstractHandler;
import com.inmaytide.orbit.service.sys.RoleService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
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
import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author Moss
 * @since October 15, 2017
 */
@Component
public class RoleHandler extends AbstractHandler {

    @Resource
    private RoleService service;

    private Role validate(Role inst) {
        Assert.isTrue(super.validate(inst)
                        || service.checkCode(inst.getCode(), inst.getId()),
                "The code cannot not be repeat");
        return inst;
    }

    @Nonnull
    public Mono<ServerResponse> list(ServerRequest request) {
        RequestPageable pageable = RequestPageable.of(request);
        return ok().body(Mono.just(service.list(pageable)), Page.class);
    }

    @Nonnull
    public Mono<ServerResponse> remove(ServerRequest request) {
        String ids = request.pathVariable("ids");
        requireNotBlank(ids);
        service.remove(ids);
        return status(NO_CONTENT).build();
    }

    @Nonnull
    public Mono<ServerResponse> get(ServerRequest request) {
        return service.get(NumberUtils.toLong(request.pathVariable("id")))
                .map(role -> ok().body(Mono.just(role), Role.class))
                .orElse(notFound().build());
    }

    @Nonnull
    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(Role.class)
                .map(this::validate)
                .map(service::insert)
                .transform(mono -> status(CREATED).body(mono, Role.class));
    }

    @Nonnull
    public Mono<ServerResponse> modify(ServerRequest request) {
        return request.bodyToMono(Role.class)
                .map(super::idRequireNonnull)
                .map(this::validate)
                .map(service::update)
                .flatMap(Mono::justOrEmpty)
                .transform(mono -> ok().body(mono, Role.class));
    }

    @Nonnull
    public Mono<ServerResponse> checkCode(ServerRequest request) {
        String code = request.pathVariable("code");
        Long id = NumberUtils.toLong(request.pathVariable("id"));
        return ok().body(Mono.just(service.checkCode(code, id)), Boolean.class);
    }

    @Nonnull
    public Mono<ServerResponse> associatePermissions(ServerRequest request) {
        String id = request.pathVariable("id");
        String permissionIds = request.pathVariable("permissionIds");
        service.associatePermissions(id, permissionIds);
        return status(NO_CONTENT).build();
    }

    @Nonnull
    public Mono<ServerResponse> associateUsers(ServerRequest request) {
        String id = request.pathVariable("id");
        String userIds = request.pathVariable("userIds");
        return ok().body(Flux.fromIterable(service.associateUsers(id, userIds)), User.class);
    }

    @Nonnull
    public Mono<ServerResponse> removeAssociatedUsers(ServerRequest request) {
        String id = request.pathVariable("id");
        String userIds = request.pathVariable("userIds");
        service.removeAssociatedUsers(id, userIds);
        return status(NO_CONTENT).build();
    }

}
