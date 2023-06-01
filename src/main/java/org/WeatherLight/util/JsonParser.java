package org.WeatherLight.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonParser() {
    }

    public static String toJson(Object object) throws IOException {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object toObject(String json, Class<?> clazz) throws IOException {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
