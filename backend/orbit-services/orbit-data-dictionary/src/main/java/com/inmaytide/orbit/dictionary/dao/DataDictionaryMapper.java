package com.inmaytide.orbit.dictionary.dao;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataDictionaryMapper extends BasicMapper<DataDictionary> {

    List<DataDictionary> listByCategory(String category);

}
