package com.inmaytide.orbit.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeUtils {

    public static final String PATTERN_DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * Format a date string and a time object into a string
     */
    public static String format(String date, LocalTime time, String inPattern, String outPattern) {
        LocalDateTime localDateTime = LocalDateTime.of(parse(date, inPattern), time);
        return format(localDateTime, outPattern);
    }

    public static String format(String date, LocalTime time, String inPattern) {
        return format(date, time, inPattern, PATTERN_DEFAULT_DATETIME);
    }

    public static String format(TemporalAccessor datetime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(datetime);
    }

    public static LocalDate parse(String date, String pattern) {
        TemporalAccessor localDate = DateTimeFormatter.ofPattern(pattern).parse(date);
        return LocalDate.from(localDate);
    }
}
