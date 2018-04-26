package com.inmaytide.orbit.commons.json.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.inmaytide.orbit.commons.util.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Moss
 * @since December 26, 2017
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        return DateTimeUtils.parse(value);
    }
}
