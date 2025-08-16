// In File: src/main/java/io/github/synte/aliva/runtime/MiscFunctions.java
package io.github.synte.aliva.runtime;

import java.text.DecimalFormat;

public class MiscFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("toNumber", (args, vars) -> {
            if (args[0] instanceof Number n) {
                return n.doubleValue();
            }
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

        // NEW: formatNumber(number, pattern)
        registry.register("formatNumber", (args, vars) -> {
            if (args.length < 2) {
                throw new RuntimeException("formatNumber(number, pattern) requires 2 arguments");
            }
            double value = (args[0] instanceof Number n)
                    ? n.doubleValue()
                    : Double.parseDouble(args[0].toString());
            String pattern = args[1].toString();
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(value);
        });
    }
}
