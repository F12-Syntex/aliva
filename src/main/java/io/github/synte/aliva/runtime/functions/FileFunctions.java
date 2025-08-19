package io.github.synte.aliva.runtime.functions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

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
        }, new FunctionData(
            "readFile",
            "Reads a text file and returns its contents, or null if it does not exist.",
            "readFile(path:string) -> string|null"
        ));

        registry.register("writeFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                if (p.getParent() != null) Files.createDirectories(p.getParent());
                Files.writeString(p, args[1].toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData(
            "writeFile",
            "Writes text content to a file, creating parent directories if necessary.",
            "writeFile(path:string, content:string)"
        ));

        registry.register("appendFile", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                if (p.getParent() != null) Files.createDirectories(p.getParent());
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
        }, new FunctionData(
            "appendFile",
            "Appends text content to a file, creating it if needed.",
            "appendFile(path:string, content:string)"
        ));

        registry.register("mkdirs", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                Files.createDirectories(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData(
            "mkdirs",
            "Creates directories recursively.",
            "mkdirs(path:string)"
        ));

        registry.register("writeBytes", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                if (p.getParent() != null) Files.createDirectories(p.getParent());
                byte[] bytes = (byte[]) args[1];
                Files.write(p, bytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write bytes to file: " + args[0], e);
            }
            return null;
        }, new FunctionData(
            "writeBytes",
            "Writes binary data to a file.",
            "writeBytes(path:string, data:byte[])"
        ));

        registry.register("readBytes", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                return Files.readAllBytes(p);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read bytes from file: " + args[0], e);
            }
        }, new FunctionData(
            "readBytes",
            "Reads a file as binary data.",
            "readBytes(path:string) -> byte[]"
        ));

        registry.register("fileExists", (args, vars) -> {
            try {
                Path p = Path.of(args[0].toString());
                return Files.exists(p);
            } catch (Exception e) {
                return false;
            }
        }, new FunctionData(
            "fileExists",
            "Checks whether a file exists.",
            "fileExists(path:string) -> boolean"
        ));
    }
}