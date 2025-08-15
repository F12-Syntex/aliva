package io.github.synte.aliva.runtime;

import java.io.IOException;
import java.nio.file.*;

public class FileFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("readFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                if (!Files.exists(p)) return null;
                return Files.readString(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        registry.register("writeFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                Files.createDirectories(p.getParent());
                Files.writeString(p, args[1].toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
        registry.register("appendFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                Files.createDirectories(p.getParent());
                Files.writeString(p, args[1].toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
}