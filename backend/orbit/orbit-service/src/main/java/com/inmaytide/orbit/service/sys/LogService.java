package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.dao.sys.LogRepository;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.log.LogServiceAdapter;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Log;
import com.inmaytide.orbit.service.basic.BasicService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LogService extends LogServiceAdapter, BasicService<LogRepository, Log, Long> {

    Page<Log> list(RequestConditions conditions, RequestPageable pageable);

    List<Log> list(RequestConditions conditions);

}
