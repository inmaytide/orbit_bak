package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.util.JsonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

/**
 * @author Moss
 * @since October 03, 2017
 */
class ThrowableTranslator {

    private final ResponseError body;

    private ThrowableTranslator(Throwable throwable, String path) {
        body = ResponseError.withThrowable(throwable).path(path).build();
    }

    static <T extends Throwable> Mono<ThrowableTranslator> translate(final Mono<T> throwable, String path) {
        return throwable.flatMap(error -> Mono.just(new ThrowableTranslator(error, path)));
    }

    Mono<Void> write(ServerHttpResponse response) {
        response.setStatusCode(getHttpStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory().wrap(JsonUtils.serializeAsBytes(getBody()));
        Mono<DataBuffer> mono = Mono.just(buffer);
        return response.writeAndFlushWith(Mono.just(mono));
    }

    Mono<ServerResponse> getResponse() {
        return status(getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(Mono.just(getBody()), ResponseError.class);
    }

    private HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(body.getStatus());
    }

    private ResponseError getBody() {
        return body;
    }

}
