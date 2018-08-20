package com.inmaytide.orbit.commons.database.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Associated {

    String table();

    String target();

    String source();

}
