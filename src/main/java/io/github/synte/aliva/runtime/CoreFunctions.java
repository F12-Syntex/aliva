package io.github.synte.aliva.runtime;

public class CoreFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("print", (args, vars) -> {
            doPrint(args, false);
            return null;
        });
        registry.register("println", (args, vars) -> {
            doPrint(args, true);
            return null;
        });
    }

    private static void doPrint(Object[] args, boolean newline) {
        for (int i = 0; i < args.length; i++) {
            Object val = args[i];
            if (val instanceof Double d && d % 1 == 0) {
                System.out.print((long) d.doubleValue());
            } else {
                System.out.print(val != null ? val : "null");
            }
            if (i < args.length - 1) System.out.print(" ");
        }
        if (newline) System.out.println();
    }
}