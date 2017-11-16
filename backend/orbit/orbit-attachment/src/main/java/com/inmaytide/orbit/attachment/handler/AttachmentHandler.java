package com.inmaytide.orbit.attachment.handler;

import com.inmaytide.orbit.attachment.enums.AttachmentStatus;
import com.inmaytide.orbit.attachment.service.AttachmentService;
import com.inmaytide.orbit.attachment.util.AttachmentUtils;
import com.inmaytide.orbit.attachment.util.FileUtils;
import com.inmaytide.orbit.domain.attachment.Attachment;
import com.inmaytide.orbit.exception.PathNotFoundException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 26, 2017
 */
@Component
public class AttachmentHandler {

    private static final String DEFAULT_FORM_ATTACHMENT_NAME = "attachment";

    @Autowired
    private AttachmentService service;

    public Mono<ServerResponse> upload(ServerRequest request) {
        String formName = request.queryParam("formName").orElse(DEFAULT_FORM_ATTACHMENT_NAME);
        return FileUtils.upload(request, formName)
                .map(service::insert)
                .transform(mono -> ok().body(mono, Attachment.class));
    }

    public Mono<ServerResponse> download(ServerRequest request) {
        Long id = NumberUtils.toLong(request.pathVariable("id"));
        return request.queryParam("status")
                .map(NumberUtils::toInt)
                .map(AttachmentStatus::valueOf)
                .map(status -> service.getByStatus(id, status))
                .orElse(service.get(id))
                .map(AttachmentUtils::getResource)
                .map(FileUtils::download)
                .orElseThrow(() -> new PathNotFoundException(request.path()));
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        service.remove(request.pathVariable("ids"));
        return noContent().build();
    }

    public Mono<ServerResponse> removeByBelong(ServerRequest request) {
        request.queryParam("belong")
                .ifPresent(service::removeByBelong);
        return noContent().build();
    }

}
