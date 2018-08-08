package com.inmaytide.orbit.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final DateTimeFormatter FORMATTER_DEFAULT_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_DEFAULT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_DEFAULT_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static String format(LocalDateTime datetime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(datetime);
    }

    public static String format(LocalDateTime datetime) {
        return FORMATTER_DEFAULT_DATETIME.format(datetime);
    }

    public static String format(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    public static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, FORMATTER_DEFAULT_DATETIME);
    }

    public static LocalDateTime parse(String value, String pattern) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    }
}
