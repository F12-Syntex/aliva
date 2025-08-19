package io.github.synte.aliva.runtime;

import java.util.HashMap;
import java.util.Map;

import io.github.synte.aliva.runtime.functions.DSLFunction;

public class FunctionRegistry {
    private final Map<String, DSLFunction> functions = new HashMap<>();

    public void register(String name, DSLFunctionExecution fn, FunctionData metadata) {
        DSLFunction function = new DSLFunction(fn, metadata);
        functions.put(name, function);
    }

    public Object invoke(String name, Object[] args, Map<String, Object> vars) {
        DSLFunctionExecution fn = functions.get(name).getExecution();
        if (fn == null) throw new RuntimeException("Unknown function: " + name);
        return fn.apply(args, vars);
    }

    public Map<String, DSLFunction> getAll() {
        return functions;
    }
}