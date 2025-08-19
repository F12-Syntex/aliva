package io.github.synte.aliva.runtime.functions;

import io.github.synte.aliva.runtime.DSLFunctionExecution;
import io.github.synte.aliva.runtime.DocumentedFunction;

public abstract class BaseFunction implements DSLFunctionExecution, DocumentedFunction {
    private final String name;
    private final String description;
    private final String usage;
    private final String returnType;
    private final String parameterDescription;

    protected BaseFunction(String name, String description, String usage, String returnType, String parameterDescription) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.returnType = returnType;
        this.parameterDescription = parameterDescription;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getReturnType() {
        return returnType;
    }

    @Override
    public String getParameterDescription() {
        return parameterDescription;
    }

    protected void validateArgumentCount(Object[] args, int expected) {
        if (args.length != expected) {
            throw new IllegalArgumentException(String.format(
                "Function '%s' expects %d arguments, but got %d",
                name, expected, args.length
            ));
        }
    }

    protected void validateArgumentCount(Object[] args, int min, int max) {
        if (args.length < min || args.length > max) {
            throw new IllegalArgumentException(String.format(
                "Function '%s' expects between %d and %d arguments, but got %d",
                name, min, max, args.length
            ));
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T getArgument(Object[] args, int index, Class<T> type) {
        if (index >= args.length) {
            throw new IllegalArgumentException(String.format(
                "Function '%s' expects argument at index %d, but only %d arguments were provided",
                name, index, args.length
            ));
        }

        Object arg = args[index];
        if (arg == null || !type.isAssignableFrom(arg.getClass())) {
            throw new IllegalArgumentException(String.format(
                "Function '%s' expects argument %d to be of type %s, but got %s",
                name, index, type.getSimpleName(),
                arg == null ? "null" : arg.getClass().getSimpleName()
            ));
        }

        return (T) arg;
    }
}