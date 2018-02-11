package com.inmaytide.orbit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonUtils {

    public static byte[] getJsonBytes(Object o) {
        try {
            return new ObjectMapper().writerFor(o.getClass()).writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJsonString(Object instance) {
        try {
            return new ObjectMapper().writerFor(instance.getClass()).writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode readJsonString(String str) {
        try {
            return new ObjectMapper().readerFor(ObjectNode.class).readValue(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
