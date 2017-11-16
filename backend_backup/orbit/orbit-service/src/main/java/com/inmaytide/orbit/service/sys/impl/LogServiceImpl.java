package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.LogRepository;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Log;
import com.inmaytide.orbit.holder.WebExchangeHolder;
import com.inmaytide.orbit.log.LogAnnotation;
import com.inmaytide.orbit.pk.IdGenerator;
import com.inmaytide.orbit.service.sys.LogService;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    @Resource
    private LogRepository logRepository;

    @Override
    public Page<Log> list(RequestConditions conditions, RequestPageable pageModel) {
        Pageable pageable = pageModel.transform(Sort.Direction.DESC, "time");
        pagingInformation(conditions, pageable);
        List<Log> content = getRepository().findList(conditions.getConditions());
        log.debug("Get logs with conditions [{}]", conditions.toString());
        return new PageImpl<>(content, pageable, getRepository().findCount(conditions.getConditions()));
    }

    @Override
    public List<Log> list(RequestConditions conditions) {
        log.debug("Get logs with conditions [{}]", conditions.toString());
        return getRepository().findList(conditions.getConditions());
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void record(JoinPoint point, Throwable e) {
        Log inst = createInstance(point, false);
        inst.setMessage(String.format("%s => %s", e.getClass().getName(), e.getMessage()));
        getRepository().insert(inst);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void record(JoinPoint point) {
        getRepository().insert(createInstance(point, true));
    }

    private Log createInstance(JoinPoint point, boolean isSucceed) {
        LogAnnotation annotation = getLogAnnotation(point);
        Log inst = new Log();
        inst.setId(IdGenerator.nextId());
        inst.setName(annotation.value());
        inst.setMethodName(point.getSignature().getName());
        inst.setClassName(point.getSignature().getDeclaringTypeName());
        getIpAddress().ifPresent(inst::setIpAddress);
        inst.setIsSucceed(isSucceed ? annotation.success() : annotation.failure());
        return inst;
    }

    private Optional<String> getIpAddress() {
        ServerHttpRequest request = WebExchangeHolder.get().getRequest();
        InetSocketAddress address = request.getRemoteAddress();
        return address != null
                ? Optional.ofNullable(address.getHostString())
                : Optional.empty();
    }

    @Override
    public LogRepository getRepository() {
        return logRepository;
    }
}
