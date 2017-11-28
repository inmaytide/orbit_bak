package com.inmaytide.orbit.dictionary.dao;

import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @author Moss
 * @since November 28, 2017
 */
public interface DataDictionaryRepository extends ReactiveCrudRepository<DataDictionary, Long> {

    Flux<DataDictionary> findByCategory(String category);

}
