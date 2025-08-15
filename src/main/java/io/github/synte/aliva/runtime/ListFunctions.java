package io.github.synte.aliva.runtime;

import java.util.*;

public class ListFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("length", (args, vars) -> (args[0] instanceof List<?> list) ? list.size() : args[0].toString().length());
        registry.register("get", (args, vars) -> ((List<?>) args[0]).get(((Double) args[1]).intValue()));
        registry.register("append", (args, vars) -> { ((List<Object>) args[0]).add(args[1]); return null; });
        registry.register("sortBy", (args, vars) -> sortBy(args));
        registry.register("range", (args, vars) -> range(args));
        registry.register("indexOf", (args, vars) -> (double) ((List<?>) args[0]).indexOf(args[1]));
        registry.register("reverse", (args, vars) -> { List<Object> copy = new ArrayList<>((List<?>) args[0]); Collections.reverse(copy); return copy; });
        registry.register("slice", (args, vars) -> new ArrayList<>(((List<?>) args[0]).subList(((Number) args[1]).intValue(), Math.min(((Number) args[2]).intValue(), ((List<?>) args[0]).size()))));
        registry.register("unique", (args, vars) -> new ArrayList<>(new LinkedHashSet<>((List<?>) args[0])));
        registry.register("flatten", (args, vars) -> flatten(args));
        registry.register("repeat", (args, vars) -> {
            int times = ((Number) args[1]).intValue();
            List<Object> out = new ArrayList<>();
            for (int i = 0; i < times; i++) out.add(args[0]);
            return out;
        });
    }

    private static List<Map<String, Object>> sortBy(Object[] args) {
        List<?> list = (List<?>) args[0];
        String key = (String) args[1];
        List<Map<String, Object>> copy = new ArrayList<>();
        for (Object o : list) {
            if (o instanceof Map<?, ?>) {
                copy.add((Map<String, Object>) o);
            }
        }
        copy.sort(Comparator.comparingDouble(m -> ((Number) m.get(key)).doubleValue()));
        return copy;
    }

    private static List<Object> range(Object[] args) {
        List<Object> result = new ArrayList<>();
        if (args.length == 2) {
            int start = ((Number) args[0]).intValue();
            int end = ((Number) args[1]).intValue();
            for (int i = start; i <= end; i++) {
                result.add((double) i);
            }
        } else if (args.length == 3) {
            int start = ((Number) args[0]).intValue();
            int end = ((Number) args[1]).intValue();
            int step = ((Number) args[2]).intValue();
            for (int i = start; i <= end; i += step) {
                result.add((double) i);
            }
        } else {
            throw new RuntimeException("range expects 2 or 3 arguments");
        }
        return result;
    }

    private static List<Object> flatten(Object[] args) {
        List<Object> flat = new ArrayList<>();
        for (Object o : (List<?>) args[0]) {
            if (o instanceof List<?> inner) {
                flat.addAll(inner);
            } else {
                flat.add(o);
            }
        }
        return flat;
    }
}