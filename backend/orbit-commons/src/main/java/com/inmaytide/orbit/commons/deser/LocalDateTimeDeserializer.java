package com.inmaytide.orbit.commons.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.inmaytide.orbit.commons.ser.LocalDateTimeSerializer;
import com.inmaytide.orbit.constant.Constants;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Moss
 * @since December 26, 2017
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText();
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constants.PATTERN_DEFAULT_DATETIME));
    }
}
