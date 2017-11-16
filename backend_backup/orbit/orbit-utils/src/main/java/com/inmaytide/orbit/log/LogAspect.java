package com.inmaytide.orbit.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final LogServiceAdapter logService;

    @Autowired
    public LogAspect(LogServiceAdapter logService) {
        this.logService = logService;
    }

    @Before(value = "@annotation(com.inmaytide.orbit.log.LogAnnotation)")
    public void beforeAdvice(JoinPoint point) {

    }

    @AfterReturning(value = "@annotation(com.inmaytide.orbit.log.LogAnnotation)")
    public void afterReturnAdvice(JoinPoint point) {
        logService.record(point);
    }

    @AfterThrowing(value = "@annotation(com.inmaytide.orbit.log.LogAnnotation)", throwing = "e")
    public void afterThrowAdvice(JoinPoint point, Throwable e) {
        logService.record(point, e);
    }
}
