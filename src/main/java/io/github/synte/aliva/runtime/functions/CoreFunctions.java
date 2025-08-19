package io.github.synte.aliva.runtime.functions;

import java.util.List;
import java.util.Map;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class CoreFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("print", (args, vars) -> {
            String s = buildString(args);
            System.out.print(s);
            return s; // also return the printed string so tests can assert without capturing stdout
        }, new FunctionData(
                "print",
                "Prints arguments without a trailing newline. Also returns the printed string.",
                "print(...values) -> string"
        ));

        registry.register("println", (args, vars) -> {
            String s = buildString(args);
            System.out.println(s);
            return s; // also return the printed string so tests can assert without capturing stdout
        }, new FunctionData(
                "println",
                "Prints arguments followed by a newline. Also returns the printed string.",
                "println(...values) -> string"
        ));

        registry.register("get", (args, vars) -> {
            Object target = args[0];
            Object key = args[1];
            if (target instanceof List) {
                if (!(key instanceof Number)) {
                    throw new RuntimeException("get() key for List must be a number, got: " + key);
                }
                List<?> list = (List<?>) target;
                int idx = ((Number) key).intValue();
                if (idx < 0 || idx >= list.size()) {
                    throw new RuntimeException("get() index out of range: " + idx + " (size=" + list.size() + ")");
                }
                return list.get(idx);
            } else if (target instanceof Map) {
                return ((Map<?, ?>) target).get(key);
            }
            throw new RuntimeException("get() target is not list/map — got: "
                    + (target == null ? "null" : target.getClass().getName()));
        }, new FunctionData(
                "get",
                "Safely gets a value from a list by index (with bounds check) or a map by key.",
                "get(target:list|map, key:number|string) -> any"
        ));

        registry.register("set", (args, vars) -> {
            Object target = args[0];
            Object key = args[1];
            Object value = args[2];
            if (target instanceof List) {
                try {
                    ((List) target).set(((Number) key).intValue(), value);
                } catch (UnsupportedOperationException uoe) {
                    throw new RuntimeException("set() requires a mutable List (e.g., ArrayList)", uoe);
                } catch (IndexOutOfBoundsException ioobe) {
                    throw new RuntimeException("set() index out of range: " + key, ioobe);
                }
            } else if (target instanceof Map) {
                ((Map) target).put(key.toString(), value);
            } else {
                throw new RuntimeException("set() target is not list/map — got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
            return null;
        }, new FunctionData(
                "set",
                "Sets a value on a list by index or a map by key. List must be mutable.",
                "set(target:list|map, key:number|string, value:any)"
        ));

        registry.register("append", (args, vars) -> {
            Object target = args[0];
            Object value = args[1];
            if (!(target instanceof List)) {
                throw new RuntimeException(
                        "append() target is not a list — got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
            try {
                ((List) target).add(value);
            } catch (UnsupportedOperationException uoe) {
                throw new RuntimeException("append() requires a mutable List (e.g., ArrayList)", uoe);
            }
            return null;
        }, new FunctionData(
                "append",
                "Appends a value to a list. List must be mutable.",
                "append(list, value:any)"
        ));

        registry.register("classOf", (args, vars) -> {
            Object obj = args[0];
            if (obj == null) {
                return "null";
            }
            String simple = obj.getClass().getSimpleName();
            return (simple != null && !simple.isEmpty())
                    ? simple
                    : obj.getClass().getName();
        }, new FunctionData(
                "classOf",
                "Returns the runtime class name of a value.",
                "classOf(value:any) -> string"
        ));

        registry.register("isList", (args, vars) -> args[0] instanceof List, new FunctionData(
                "isList",
                "Checks if a value is a list.",
                "isList(value:any) -> boolean"
        ));

        registry.register("isMap", (args, vars) -> args[0] instanceof Map, new FunctionData(
                "isMap",
                "Checks if a value is a map.",
                "isMap(value:any) -> boolean"
        ));

        registry.register("toString", (args, vars) -> {
            if (args.length == 0 || args[0] == null) {
                return "";
            }
            return String.valueOf(args[0]);
        }, new FunctionData(
                "toString",
                "Converts a value to a string, null becomes empty string.",
                "toString(value:any) -> string"
        ));
    }

    private static String buildString(Object[] args) {
        StringBuilder sb = new StringBuilder();
        boolean appended = false;
        for (Object val : args) {
            String str
                    = val == null ? "null"
                            : (val instanceof Double d && d % 1 == 0) ? Long.toString(d.longValue())
                                    : val.toString();

            if (!str.isEmpty()) {
                if (appended) {
                    sb.append(' ');
                }
                sb.append(str);
                appended = true;
            }
        }
        return sb.toString();
    }
}
