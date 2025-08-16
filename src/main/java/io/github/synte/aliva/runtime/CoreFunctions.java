package io.github.synte.aliva.runtime;

import java.util.List;
import java.util.Map;

public class CoreFunctions {

    public static void register(FunctionRegistry registry) {
        // Basic prints
        registry.register("print", (args, vars) -> {
            doPrint(args, false);
            return null;
        });
        registry.register("println", (args, vars) -> {
            doPrint(args, true);
            return null;
        });

        // Safe get for Lists and Maps
        registry.register("get", (args, vars) -> {
            Object target = args[0];
            Object key = args[1];
            if (target instanceof List) {
                if (!(key instanceof Number)) {
                    throw new RuntimeException("get() key for List must be a number, got: " + key);
                }
                return ((List<?>) target).get(((Number) key).intValue());
            } else if (target instanceof Map) {
                return ((Map<?, ?>) target).get(key);
            }
            throw new RuntimeException("get() target is not list/map — got: "
                    + (target == null ? "null" : target.getClass().getName()));
        });

        // Safe set for Lists and Maps
        registry.register("set", (args, vars) -> {
            Object target = args[0];
            Object key = args[1];
            Object value = args[2];
            if (target instanceof List) {
                ((List) target).set(((Number) key).intValue(), value);
            } else if (target instanceof Map) {
                ((Map) target).put(key.toString(), value);
            } else {
                throw new RuntimeException("set() target is not list/map — got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
            return null;
        });

        // Safe append for Lists
        registry.register("append", (args, vars) -> {
            Object target = args[0];
            Object value = args[1];
            if (!(target instanceof List)) {
                throw new RuntimeException(
                        "append() target is not a list — got: "
                        + (target == null ? "null" : target.getClass().getName()));
            }
            ((List) target).add(value);
            return null;
        });

        // Type name
        registry.register("classOf", (args, vars) -> {
            Object obj = args[0];
            return obj == null ? "null" : obj.getClass().getName();
        });

        // Type checks
        registry.register("isList", (args, vars) -> args[0] instanceof List);
        registry.register("isMap", (args, vars) -> args[0] instanceof Map);
        registry.register("toString", (args, vars) -> {
            if (args.length == 0 || args[0] == null) {
                return "";
            }
            return String.valueOf(args[0]);
        });
    }

    private static void doPrint(Object[] args, boolean newline) {
        for (int i = 0; i < args.length; i++) {
            Object val = args[i];
            if (val instanceof Double d && d % 1 == 0) {
                System.out.print((long) d.doubleValue());
            } else {
                System.out.print(val != null ? val : "null");
            }
            if (i < args.length - 1) {
                System.out.print(" ");
            }
        }
        if (newline) {
            System.out.println();
        }
    }
}
