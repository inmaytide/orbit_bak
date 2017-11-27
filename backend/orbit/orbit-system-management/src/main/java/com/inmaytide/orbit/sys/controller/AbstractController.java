package com.inmaytide.orbit.sys.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public abstract class AbstractController {

    protected void processBindingResult(BindingResult bind) {
        if (bind.hasErrors()) {
            String message = StringUtils.join(bind.getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .toArray(String[]::new), ",");
            throw new IllegalArgumentException(message);
        }
    }

}
