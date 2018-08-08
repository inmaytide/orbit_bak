package com.inmaytide.orbit.dictionary.dao;

import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Moss
 * @since November 28, 2017
 */
public interface DataDictionaryRepository extends JpaRepository<DataDictionary, Long> {

    @Cacheable(value = "dictionaries", key = "#p0")
    List<DataDictionary> findByCategory(String category, Sort sort);

}
