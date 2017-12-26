package com.inmaytide.orbit.commons.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.inmaytide.orbit.commons.util.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Moss
 * @since December 17, 2017
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final String DEFAULT_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateTimeUtils.format(value, DEFAULT_DATETIME_FORMAT_PATTERN));
    }

}
