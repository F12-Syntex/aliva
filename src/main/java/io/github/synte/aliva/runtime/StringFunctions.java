package io.github.synte.aliva.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            for (Object a : args) {
                sb.append(a != null ? a.toString() : "null");
            }
            return sb.toString();
        });
        registry.register("lower", (args, vars) -> String.valueOf(args[0]).toLowerCase());
        registry.register("contains", (args, vars) -> String.valueOf(args[0])
                .contains(String.valueOf(args[1])));
        registry.register("matches", (args, vars) -> String.valueOf(args[0])
                .matches(String.valueOf(args[1])));
        registry.register("sanitizeFilename", (args, vars) -> String.valueOf(args[0])
                .replaceAll("[^a-zA-Z0-9-_]", "_").trim());
        registry.register("replaceAll", (args, vars) -> {
            if (args.length < 3) {
                throw new RuntimeException("replaceAll(text, target, replacement) requires 3 arguments");
            }
            String text = args[0] != null ? args[0].toString() : "";
            String target = args[1] != null ? args[1].toString() : "";
            String replacement = args[2] != null ? args[2].toString() : "";
            return text.replace(target, replacement);
        });
        registry.register("urlSlug", (args, vars) -> {
            String url = String.valueOf(args[0]);
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            String[] parts = url.split("/");
            String last = parts[parts.length - 1];
            int qIdx = last.indexOf('?');
            if (qIdx >= 0) {
                last = last.substring(0, qIdx);
            }
            return last.trim();
        });
        registry.register("getNumbers", (args, vars) -> {
            String input = String.valueOf(args[0]);
            java.util.List<String> numbers = new ArrayList<>();
            Matcher m = Pattern.compile("\\d+").matcher(input);
            while (m.find()) {
                numbers.add(m.group());
            }
            return numbers;
        });
    }

    private static List<String> castToStringList(Object obj) {
        if (obj instanceof List<?> rawList) {
            return rawList.stream().map(o -> o != null ? o.toString() : "null").toList();
        }
        throw new RuntimeException("Expected a list for join()");
    }
}
