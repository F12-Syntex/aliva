package io.github.synte.aliva.runtime;

import java.util.HashMap;
import java.util.Map;

public class FunctionRegistry {
    private final Map<String, DSLFunction> functions = new HashMap<>();

    public void register(String name, DSLFunction fn) {
        functions.put(name, fn);
    }

    public Object invoke(String name, Object[] args, Map<String, Object> vars) {
        DSLFunction fn = functions.get(name);
        if (fn == null) throw new RuntimeException("Unknown function: " + name);
        return fn.apply(args, vars);
    }
}