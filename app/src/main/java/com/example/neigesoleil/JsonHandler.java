package com.example.neigesoleil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class JsonHandler {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        // Ignore non setable data to the object
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }


    // Convert a String to Json node
    public static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);
    }

    public static <A> A fromJson (JsonNode node, Class<A> clazz) throws JsonProcessingException, IllegalArgumentException {
        return objectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object a) {

        return objectMapper.valueToTree(a);
    }

    public static String toString(JsonNode node) throws IOException {
        return generateString(node, false);
    }

    public static String prettyPrint(JsonNode node) throws IOException {
        return generateString(node, true);
    }


    private static String generateString(JsonNode node, boolean pretty) throws IOException {
        ObjectWriter objectWriter = objectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(node);
    }
}
