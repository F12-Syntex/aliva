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
        DSLFunction fnObj = functions.get(name);
        if (fnObj == null || fnObj.getExecution() == null) {
            throw new RuntimeException("Unknown function: " + name);
        }
        DSLFunctionExecution fn = fnObj.getExecution();
        return fn.apply(args, vars);
    }

    public Map<String, DSLFunction> getAll() {
        return functions;
    }
}