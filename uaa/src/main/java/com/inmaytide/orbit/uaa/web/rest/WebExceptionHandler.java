package com.inmaytide.orbit.uaa.web.rest;

import com.inmaytide.exception.http.handler.servlet.DefaultExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luomiao
 * @since 2019/09/12
 */
@RestControllerAdvice
public class WebExceptionHandler {

    @Autowired
    private DefaultExceptionHandler exceptionHandler;

    @ExceptionHandler(Throwable.class)
    public void handler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        exceptionHandler.handle(request, response, e);
    }

}
