package com.inmaytide.orbit.handler.sys;

import com.inmaytide.orbit.consts.UserStatus;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Attachment;
import com.inmaytide.orbit.handler.AbstractHandler;
import com.inmaytide.orbit.attachment.util.FileUtils;
import com.inmaytide.orbit.service.sys.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 25, 2017
 */
@Component
public class UserHandler extends AbstractHandler {

    private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    @Resource
    private UserService service;

    @Nonnull
    @RequiresPermissions("user:list")
    public Mono<ServerResponse> list(ServerRequest request) {
        Mono<Page> mono = Mono.just(service.list(buildConditions(request), RequestPageable.of(request)));
        return ok().body(mono, Page.class);
    }

    @Nonnull
    public Mono<ServerResponse> uploadAvatar(ServerRequest request) {
        String id = request.pathVariable("id");
        return FileUtils.upload(request, "avatar")
                .transform(mono -> ok().body(mono, Attachment.class));
    }

    private RequestConditions buildConditions(ServerRequest request) {
        RequestConditions conditions = new RequestConditions(request);
        conditions.fromRequest("keywords");
        conditions.fromRequest("status", value -> UserStatus.valueOf(value).toString());
        conditions.fromRequest("orgs");
        return conditions;
    }

}
