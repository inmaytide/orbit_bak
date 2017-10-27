package com.inmaytide.orbit.attachment.handler;

import com.inmaytide.orbit.attachment.util.AttachmentUtils;
import com.inmaytide.orbit.attachment.util.FileUtils;
import com.inmaytide.orbit.attachment.service.AttachmentService;
import com.inmaytide.orbit.domain.sys.Attachment;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 26, 2017
 */
public class AttachmentHandler {

    private static final String DEFAULT_FORM_ATTACHMENT_NAME = "attachment";

    @Resource
    private AttachmentHandler handler;

    @Resource
    private AttachmentService service;

    public RouterFunction<?> routers() {
        RouterFunction<?> routers = route(POST("/").and(accept(MULTIPART_FORM_DATA)), handler::uploadAttachment)
                .and(route(GET("/{id}"), handler::downloadAttachment));
        return nest(path("/attachments"), routers);
    }

    @Nonnull
    public Mono<ServerResponse> uploadAttachment(ServerRequest request) {
        String formName = request.queryParam("formName").orElse(DEFAULT_FORM_ATTACHMENT_NAME);
        return FileUtils.upload(request, formName)
                .transform(mono -> ok().body(mono, Attachment.class));
    }

    @Nonnull
    public Mono<ServerResponse> downloadAttachment(ServerRequest request) {
        Long id = NumberUtils.toLong(request.pathVariable("id"));
        return request.queryParam("status")
                .map(status -> service.getByStatus(id, status))
                .orElse(service.get(id))
                .map(attachment -> FileUtils.download(AttachmentUtils.getResource(attachment)))
                .orElse(notFound().build());
    }

}
