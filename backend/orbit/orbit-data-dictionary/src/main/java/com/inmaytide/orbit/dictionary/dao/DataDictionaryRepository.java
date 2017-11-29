package com.inmaytide.orbit.dictionary.dao;

import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Moss
 * @since November 28, 2017
 */
public interface DataDictionaryRepository extends JpaRepository<DataDictionary, Long> {

    List<DataDictionary> findByCategory(String category);

}
