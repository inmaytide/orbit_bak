package com.inmaytide.orbit.core.id.rest;

import com.inmaytide.orbit.core.id.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

/**
 * @author luomiao
 * @since 2019/09/09
 */
@RestController
@RequestMapping("/api")
public class IdResource {

    private static final Integer BATCH_QUANTITY = 10;

    private final IdGenerator<Long> idGenerator;

    @Autowired
    public IdResource(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @GetMapping("/id")
    public Mono<Long> generate() {
        return Mono.just(idGenerator.generate());
    }

    @GetMapping("/ids")
    public Flux<Long> batchGenerate() {
        return Flux.fromStream(IntStream.range(0, BATCH_QUANTITY).mapToObj(i -> idGenerator.generate()));
    }
}
