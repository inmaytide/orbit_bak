package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.consts.DataDictionaryCategory;
import com.inmaytide.orbit.dao.sys.DataDictionaryRepository;
import com.inmaytide.orbit.domain.sys.DataDictionary;
import com.inmaytide.orbit.service.sys.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Moss
 * @since November 07, 2017
 */
@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "sort");

    @Autowired
    private DataDictionaryRepository repository;

    @Override
    @Cacheable(cacheNames = "data_dictionary", key = "#category")
    public List<DataDictionary> listByCategory(DataDictionaryCategory category) {
        return getRepository().findByCategory(category.getValue(), DEFAULT_SORT);
    }

    @Override
    @Cacheable(cacheNames = "data_dictionary", key = "#category")
    public List<DataDictionary> listByCategory(String category) {
        return getRepository().findByCategory(category, DEFAULT_SORT);
    }

    @Override
    public DataDictionaryRepository getRepository() {
        return repository;
    }
}
