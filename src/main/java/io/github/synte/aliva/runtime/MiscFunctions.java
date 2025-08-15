package io.github.synte.aliva.runtime;

public class MiscFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("toNumber", (args, vars) -> {
            if (args[0] instanceof Number n) return n.doubleValue();
            try {
                return Double.parseDouble(args[0].toString());
            } catch (Exception e) {
                throw new RuntimeException("Cannot convert to number: " + args[0]);
            }
        });
        registry.register("sleep", (args, vars) -> {
            try {
                Thread.sleep(((Number) args[0]).longValue());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return null;
        });
        registry.register("random", (args, vars) -> Math.random());
    }
}