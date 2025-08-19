package io.github.synte.aliva.runtime;

import java.util.Map;

public interface DSLFunctionExecution {
    public Object apply(Object[] args, Map<String, Object> variables);
}