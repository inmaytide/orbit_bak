package com.inmaytide.orbit.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public interface LogServiceAdapter {

    void record(JoinPoint point, Throwable e);

    void record(JoinPoint point);

    default LogAnnotation getLogAnnotation(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getMethod().getAnnotation(LogAnnotation.class);
    }

}
