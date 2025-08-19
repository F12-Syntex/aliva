package io.github.synte.aliva.runtime.functions;

import java.util.List;
import java.util.Map;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class CoreFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("print", (args, vars) -> {
            doPrint(args, false);
            return null;
        }, new FunctionData(
            "print",
            "Prints arguments without a trailing newline.",
            "print(...values)"
        ));

        registry.register("println", (args, vars) -> {
            doPrint(args, true);
            return null;
        }, new FunctionData(
            "println",
            "Prints arguments followed by a newline.",
            "println(...values)"
        ));

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
        }, new FunctionData(
            "get",
            "Safely gets a value from a list by index or a map by key.",
            "get(target:list|map, key:number|string) -> any"
        ));

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
        }, new FunctionData(
            "set",
            "Sets a value on a list by index or a map by key.",
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
            ((List) target).add(value);
            return null;
        }, new FunctionData(
            "append",
            "Appends a value to a list.",
            "append(list, value:any)"
        ));

        registry.register("classOf", (args, vars) -> {
            Object obj = args[0];
            return obj == null ? "null" : obj.getClass().getName();
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