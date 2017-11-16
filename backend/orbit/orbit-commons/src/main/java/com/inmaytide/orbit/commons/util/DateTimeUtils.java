package com.inmaytide.orbit.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {


    public static String format(LocalDateTime time, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(time);
    }

    public static String formatNow(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }
}
