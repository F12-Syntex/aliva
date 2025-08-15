package io.github.synte.aliva.runtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("readFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                if (!Files.exists(p)) {
                    return null;
                }
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
                Files.writeString(
                    p,
                    args[1].toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        registry.register("mkdirs", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                Files.createDirectories(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        // NEW: writeBytes for binary data
        registry.register("writeBytes", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                Files.createDirectories(p.getParent());
                byte[] bytes = (byte[]) args[1];
                Files.write(p, bytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write bytes to file: " + args[0], e);
            }
            return null;
        });
    }
}