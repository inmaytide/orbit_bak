package com.inmaytide.orbit.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出列信息配置<br/>
 * 如果属性上没有配置此注解，属性不会被导出
 * @author Moss
 * @since  September 8, 2017
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
    /**
     * 列标题文字, 有模板时此配置无效<br/>
     * 没有模板时此配置不能为空
     */
    String header() default "";

    /**
     * 属性在excel中列下标
     */
    int column();

}
