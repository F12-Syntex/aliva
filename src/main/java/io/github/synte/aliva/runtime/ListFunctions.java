package io.github.synte.aliva.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ListFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("length", (args, vars) -> (args[0] instanceof List<?> list) ? list.size() : args[0].toString().length());
        registry.register("get", (args, vars) -> {
            Object target = args[0];
            Object key = args[1];

            if (target instanceof java.util.List) {
                if (!(key instanceof Number)) {
                    throw new RuntimeException("get() key for List must be a number, got: " + key);
                }
                return ((java.util.List<?>) target).get(((Number) key).intValue());
            } else if (target instanceof java.util.Map) {
                return ((java.util.Map<?, ?>) target).get(key);
            } else {
                throw new RuntimeException("get() target is not list/map — got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
        });
        registry.register("append", (args, vars) -> {
            ((List<Object>) args[0]).add(args[1]);
            return null;
        });
        registry.register("sortBy", (args, vars) -> sortBy(args));
        registry.register("range", (args, vars) -> range(args));
        registry.register("indexOf", (args, vars) -> (double) ((List<?>) args[0]).indexOf(args[1]));
        registry.register("reverse", (args, vars) -> {
            List<Object> copy = new ArrayList<>((List<?>) args[0]);
            Collections.reverse(copy);
            return copy;
        });
        registry.register("slice", (args, vars) -> new ArrayList<>(((List<?>) args[0]).subList(((Number) args[1]).intValue(), Math.min(((Number) args[2]).intValue(), ((List<?>) args[0]).size()))));
        registry.register("unique", (args, vars) -> new ArrayList<>(new LinkedHashSet<>((List<?>) args[0])));
        registry.register("flatten", (args, vars) -> flatten(args));
        registry.register("repeat", (args, vars) -> {
            int times = ((Number) args[1]).intValue();
            List<Object> out = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                out.add(args[0]);
            }
            return out;
        });

        registry.register("classOf", (args, vars) -> {
            Object obj = args[0];
            return obj == null ? "null" : obj.getClass().getName();
        });

        registry.register("isList", (args, vars) -> args[0] instanceof java.util.List);

        registry.register("isMap", (args, vars) -> args[0] instanceof java.util.Map);

        registry.register("dump", (args, vars) -> {
            Object obj = args[0];
            System.out.println("[DUMP] " + deepDump(obj, 0));
            return null;
        });

    }

    private static String deepDump(Object obj, int indent) {
        if (obj == null) {
            return "null";
        }
        String pad = " ".repeat(indent);
        if (obj instanceof java.util.Map) {
            StringBuilder sb = new StringBuilder("{\n");
            java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
            for (var e : map.entrySet()) {
                sb.append(pad).append("  ").append(e.getKey()).append(": ")
                        .append(deepDump(e.getValue(), indent + 2)).append("\n");
            }
            sb.append(pad).append("}");
            return sb.toString();
        }
        if (obj instanceof java.util.List) {
            StringBuilder sb = new StringBuilder("[\n");
            java.util.List<?> list = (java.util.List<?>) obj;
            for (Object item : list) {
                sb.append(pad).append("  ").append(deepDump(item, indent + 2)).append("\n");
            }
            sb.append(pad).append("]");
            return sb.toString();
        }
        return obj.getClass().getName() + "(" + String.valueOf(obj) + ")";
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
        copy.sort(Comparator.comparingDouble(m -> {
            Object val = m.get(key);
            if (val instanceof Number n) {
                return n.doubleValue();
            }
            if (val != null) {
                try {
                    return Double.parseDouble(val.toString().trim());
                } catch (NumberFormatException e) {
                    // If value can't be parsed, push it to the end
                    return Double.POSITIVE_INFINITY;
                }
            }
            // Nulls go to the end
            return Double.POSITIVE_INFINITY;
        }));
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
