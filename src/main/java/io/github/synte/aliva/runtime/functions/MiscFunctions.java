package io.github.synte.aliva.runtime.functions;

import java.text.DecimalFormat;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

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
        }, new FunctionData(
            "toNumber",
            "Converts a value to a number or throws if it cannot be parsed.",
            "toNumber(value:any) -> number"
        ));

        registry.register("sleep", (args, vars) -> {
            try {
                Thread.sleep(((Number) args[0]).longValue());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData(
            "sleep",
            "Sleeps for the given number of milliseconds.",
            "sleep(ms:number)"
        ));

        registry.register("random", (args, vars) -> Math.random(), new FunctionData(
            "random",
            "Returns a random number in [0, 1).",
            "random() -> number"
        ));

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
        }, new FunctionData(
            "formatNumber",
            "Formats a number using a DecimalFormat pattern.",
            "formatNumber(number:number, pattern:string) -> string"
        ));
    }
}