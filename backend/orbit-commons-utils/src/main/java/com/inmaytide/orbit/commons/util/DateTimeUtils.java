package com.inmaytide.orbit.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String PATTERN_DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DEFAULT_TIME = "HH:mm:ss";


    public static String format(LocalDateTime datetime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(datetime);
    }

    public static String format(LocalDateTime datetime) {
        return format(datetime, PATTERN_DEFAULT_DATETIME);
    }

    public static String format(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    public static LocalDateTime parse(String value) {
        return parse(value, PATTERN_DEFAULT_DATETIME);
    }

    public static LocalDateTime parse(String value, String pattern) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    }
}
