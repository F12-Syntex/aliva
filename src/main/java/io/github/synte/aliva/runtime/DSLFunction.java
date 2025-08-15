package io.github.synte.aliva.runtime;

import java.util.Map;

@FunctionalInterface
public interface DSLFunction {
    Object apply(Object[] args, Map<String, Object> variables);
}