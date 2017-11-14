package com.inmaytide.orbit.auth.provider;

import org.springframework.core.io.Resource;

import java.util.function.Supplier;

/**
 * @author Moss
 * @since October 04, 2017
 */
public interface CaptchaProvider {

    Resource generateCaptcha(String format, String keySuffix);

    void validation(String captcha, String v, Supplier<? extends RuntimeException> exceptionSupplier);

}
