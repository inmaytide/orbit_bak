package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.DataDictionary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

import java.util.List;

/**
 * @author Moss
 * @since November 07, 2017
 */
public interface DataDictionaryRepository extends MybatisRepository<DataDictionary, Long> {

    List<DataDictionary> findByCategory(String category, Sort sort);

}
