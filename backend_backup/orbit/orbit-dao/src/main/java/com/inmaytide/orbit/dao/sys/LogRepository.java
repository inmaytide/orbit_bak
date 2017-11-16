package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.Log;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LogRepository extends MybatisRepository<Log, Long> {

    @Query(value = "findList")
    List<Log> findList(@Param("conditions") Map<String, Object> conditions);

    @Query(value = "findCount")
    Long findCount(@Param("conditions") Map<String, Object> conditions);
}
