package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.consts.DataDictionaryCategory;
import com.inmaytide.orbit.dao.sys.DataDictionaryRepository;
import com.inmaytide.orbit.domain.sys.DataDictionary;
import com.inmaytide.orbit.service.basic.BasicService;

import java.util.List;

/**
 * @author Moss
 * @since November 07, 2017
 */
public interface DataDictionaryService extends BasicService<DataDictionaryRepository, DataDictionary, Long> {

    List<DataDictionary> listByCategory(DataDictionaryCategory category);

    List<DataDictionary> listByCategory(String category);

}
