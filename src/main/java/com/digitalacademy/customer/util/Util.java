package com.digitalacademy.customer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;

public class Util {
    private static ObjectMapper obmap =
            new ObjectMapper().setPropertyNamingStrategy(
                    PropertyNamingStrategy.SNAKE_CASE
            );

    public static <T> T readValue(String json, Class<T> tClass) throws IOException {
        return obmap.readValue(json,tClass);
    }
}
