package io.github.synte.aliva.runtime.functions;

import java.util.Map;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class JsonFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("toJson", (args, vars) -> {
            try {
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                // Use compact JSON for stable matching in tests
                return m.writeValueAsString(args[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, new FunctionData(
            "toJson",
            "Serializes a value to a compact JSON string.",
            "toJson(value:any) -> string"
        ));

        registry.register("parseJson", (args, vars) -> {
            try {
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                return m.readValue(args[0].toString(), Object.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, new FunctionData(
            "parseJson",
            "Parses a JSON string into Java objects (Map/List/primitive).",
            "parseJson(json:string) -> any"
        ));

        registry.register("jsonToMap", (args, vars) -> {
            try {
                Object in = args[0];
                if (in instanceof Map<?, ?>) {
                    return in;
                }
                String s = in.toString();
                // Tolerate escaped quotes from DSL
                if (s.contains("\\\"")) {
                    s = s.replace("\\\"", "\"");
                }
                com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
                return m.readValue(s, Map.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse JSON into Map", e);
            }
        }, new FunctionData(
            "jsonToMap",
            "Parses a JSON object string into a Map.",
            "jsonToMap(json:string) -> map"
        ));
    }
}