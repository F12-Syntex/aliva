package io.github.synte.aliva.runtime.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class ListFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("length", (args, vars) -> {
            Object v = args[0];
            if (v instanceof List<?> list) {
                return list.size();
            }
            if (v instanceof String s) {
                return s.length();
            }
            if (v != null) {
                Class<?> cls = v.getClass();
                if (cls.isArray()) {
                    if (v instanceof byte[] ba) return ba.length;
                    if (v instanceof short[] a) return a.length;
                    if (v instanceof int[] a) return a.length;
                    if (v instanceof long[] a) return a.length;
                    if (v instanceof char[] a) return a.length;
                    if (v instanceof float[] a) return a.length;
                    if (v instanceof double[] a) return a.length;
                    if (v instanceof boolean[] a) return a.length;
                    if (v instanceof Object[] oa) return oa.length;
                    return java.lang.reflect.Array.getLength(v);
                }
            }
            return String.valueOf(v).length();
        }, new FunctionData(
            "length",
            "Returns the length of a list, string, or array (including byte[]).",
            "length(value:list|string|array) -> number"
        ));

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
                throw new RuntimeException("get() target is not list/map ΓÇö got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
        }, new FunctionData(
            "get",
            "Gets an element from a list by index or a map by key.",
            "get(target:list|map, key:number|string) -> any"
        ));

        registry.register("sortBy", (args, vars) -> sortBy(args), new FunctionData(
            "sortBy",
            "Returns a new list of maps sorted ascending by the specified key.",
            "sortBy(listOfMaps:list<map>, key:string) -> list<map>"
        ));

        registry.register("range", (args, vars) -> range(args), new FunctionData(
            "range",
            "Creates a list of numbers from start to end inclusive, with optional step.",
            "range(start:number, end:number, [step:number]) -> list<number>"
        ));

        registry.register("indexOf", (args, vars) -> (double) ((List<?>) args[0]).indexOf(args[1]), new FunctionData(
            "indexOf",
            "Returns the index of the first occurrence of value in a list, or -1.",
            "indexOf(list, value:any) -> number"
        ));

        registry.register("reverse", (args, vars) -> {
            List<Object> copy = new ArrayList<>((List<?>) args[0]);
            Collections.reverse(copy);
            return copy;
        }, new FunctionData(
            "reverse",
            "Returns a reversed copy of a list.",
            "reverse(list) -> list"
        ));

        registry.register("slice", (args, vars) -> new ArrayList<>(((List<?>) args[0]).subList(((Number) args[1]).intValue(), Math.min(((Number) args[2]).intValue(), ((List<?>) args[0]).size()))), new FunctionData(
            "slice",
            "Returns a sublist from start index (inclusive) to end index (exclusive, clamped).",
            "slice(list, start:number, end:number) -> list"
        ));

        registry.register("unique", (args, vars) -> new ArrayList<>(new LinkedHashSet<>((List<?>) args[0])), new FunctionData(
            "unique",
            "Returns a list with duplicate elements removed (stable order).",
            "unique(list) -> list"
        ));

        registry.register("flatten", (args, vars) -> flatten(args), new FunctionData(
            "flatten",
            "Flattens a one-level nested list.",
            "flatten(list<list|any>) -> list"
        ));

        registry.register("repeat", (args, vars) -> {
            int times = ((Number) args[1]).intValue();
            List<Object> out = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                out.add(args[0]);
            }
            return out;
        }, new FunctionData(
            "repeat",
            "Creates a list by repeating a value N times.",
            "repeat(value:any, times:number) -> list"
        ));

        registry.register("classOf", (args, vars) -> {
            Object obj = args[0];
            return obj == null ? "null" : obj.getClass().getName();
        }, new FunctionData(
            "classOf",
            "Returns the runtime class name of a value.",
            "classOf(value:any) -> string"
        ));

        registry.register("isList", (args, vars) -> args[0] instanceof java.util.List, new FunctionData(
            "isList",
            "Checks if a value is a list.",
            "isList(value:any) -> boolean"
        ));

        registry.register("isMap", (args, vars) -> args[0] instanceof java.util.Map, new FunctionData(
            "isMap",
            "Checks if a value is a map.",
            "isMap(value:any) -> boolean"
        ));

        registry.register("dump", (args, vars) -> {
            Object obj = args[0];
            System.out.println("[DUMP] " + deepDump(obj, 0));
            return null;
        }, new FunctionData(
            "dump",
            "Pretty-prints nested lists and maps to stdout.",
            "dump(value:any)"
        ));
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
                    return Double.POSITIVE_INFINITY;
                }
            }
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