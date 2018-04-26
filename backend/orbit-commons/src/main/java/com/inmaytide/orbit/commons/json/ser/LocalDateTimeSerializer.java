package com.inmaytide.orbit.commons.json.ser;

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

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateTimeUtils.format(value));
    }

}
