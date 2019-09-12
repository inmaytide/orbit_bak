package com.inmaytide.orbit.core.id.handler;

import com.inmaytide.orbit.core.id.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author luomiao
 * @since 2019/09/09
 */
@Component
public class Handler {

    private final IdGenerator<Long> idGenerator;

    @Autowired
    public Handler(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Mono<ServerResponse> generate(ServerRequest serverRequest) {
        return ok().body(Mono.just(idGenerator.generate()), Long.class);
    }

    public Mono<ServerResponse> batchGenerate(ServerRequest serverRequest) {
        Flux<Long> publisher = Flux.fromStream(IntStream.range(0, 10).mapToObj(i -> idGenerator.generate()));
        return ok().body(BodyInserters.fromPublisher(publisher, Long.class));
    }
}
