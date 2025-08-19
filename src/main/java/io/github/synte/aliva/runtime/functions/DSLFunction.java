package io.github.synte.aliva.runtime.functions;

import io.github.synte.aliva.runtime.DSLFunctionExecution;
import io.github.synte.aliva.runtime.FunctionData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DSLFunction {
    public DSLFunctionExecution execution;
    public FunctionData metadata;
}