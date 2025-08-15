package io.github.synte.aliva.runtime;

import java.util.Map;

public class JsonFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("toJson", (args, vars) -> {
            try {
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                m.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
                return m.writeValueAsString(args[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        registry.register("parseJson", (args, vars) -> {
            try {
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                return m.readValue(args[0].toString(), Object.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // New function: parse JSON string directly into a Map
        registry.register("jsonToMap", (args, vars) -> {
            try {
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                return m.readValue(args[0].toString(), Map.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON into Map", e);
            }
        });
    }
}