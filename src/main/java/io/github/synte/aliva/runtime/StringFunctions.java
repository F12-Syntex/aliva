package io.github.synte.aliva.runtime;

import java.util.Arrays;
import java.util.List;

public class StringFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("replace", (args, vars) -> String.valueOf(args[0])
                .replace(String.valueOf(args[1]), String.valueOf(args[2])));
        registry.register("trim", (args, vars) -> String.valueOf(args[0]).trim());
        registry.register("split", (args, vars) -> Arrays.asList(String.valueOf(args[0])
                .split(String.valueOf(args[1]))));
        registry.register("join", (args, vars) -> String.join(String.valueOf(args[1]),
                castToStringList(args[0])));
        registry.register("concat", (args, vars) -> {
            StringBuilder sb = new StringBuilder();
            for (Object a : args) sb.append(a != null ? a.toString() : "null");
            return sb.toString();
        });
        registry.register("lower", (args, vars) -> String.valueOf(args[0]).toLowerCase());
        registry.register("contains", (args, vars) -> String.valueOf(args[0])
                .contains(String.valueOf(args[1])));
        registry.register("matches", (args, vars) -> String.valueOf(args[0])
                .matches(String.valueOf(args[1])));
        registry.register("sanitizeFilename", (args, vars) -> String.valueOf(args[0])
                .replaceAll("[^a-zA-Z0-9-_]", "_").trim());
    }

    private static List<String> castToStringList(Object obj) {
        if (obj instanceof List<?> rawList) {
            return rawList.stream().map(o -> o != null ? o.toString() : "null").toList();
        }
        throw new RuntimeException("Expected a list for join()");
    }
}