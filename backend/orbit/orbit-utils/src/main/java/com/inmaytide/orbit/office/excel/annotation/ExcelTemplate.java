package com.inmaytide.orbit.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出模板配置<br/>
 * 如果没有配置模板，默认根据类的属性配置的{@link Comment}信息生成模板
 * @author Moss
 * @since  September 8, 2017
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTemplate {

    /**
     * excel模板路径在classpath下
     */
    String template() default "";

    int startRow() default 1;

    /**
     * 配置模板后数据写入的Sheet
     */
    int sheet() default 0;
}
