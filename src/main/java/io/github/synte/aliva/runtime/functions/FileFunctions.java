package io.github.synte.aliva.runtime.functions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class FileFunctions {
    // Shared HTTP client for downloadBytes

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    public static void register(FunctionRegistry registry) {
        registry.register("readFile", (args, vars) -> {
            try {
                Path p = Path.of(String.valueOf(args[0]));
                if (!Files.exists(p)) {
                    return "";
                }
                return Files.readString(p, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, new FunctionData(
                "readFile",
                "Reads a text file (UTF-8) and returns its contents, or empty string if it does not exist.",
                "readFile(path:string) -> string"
        ));

        registry.register("writeFile", (args, vars) -> {
            try {
                Path p = Path.of(String.valueOf(args[0]));
                if (p.getParent() != null) {
                    Files.createDirectories(p.getParent());
                }
                Files.writeString(p, String.valueOf(args[1]), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData(
                "writeFile",
                "Writes text content (UTF-8) to a file, creating parent directories if necessary.",
                "writeFile(path:string, content:string)"
        ));

        registry.register("appendFile", (args, vars) -> {
            try {
                Path p = Path.of(String.valueOf(args[0]));
                if (p.getParent() != null) {
                    Files.createDirectories(p.getParent());
                }
                Files.writeString(
                        p,
                        String.valueOf(args[1]),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData(
                "appendFile",
                "Appends text content (UTF-8) to a file, creating it if needed.",
                "appendFile(path:string, content:string)"
        ));

        registry.register("mkdirs", (args, vars) -> {
            try {
                Path p = Path.of(String.valueOf(args[0]));
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
                Path p = Path.of(String.valueOf(args[0]));
                if (p.getParent() != null) {
                    Files.createDirectories(p.getParent());
                }
                Object data = args[1];
                if (!(data instanceof byte[])) {
                    throw new IllegalArgumentException("writeBytes expects data:byte[]");
                }
                byte[] bytes = (byte[]) data;
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
                Path p = Path.of(String.valueOf(args[0]));
                if (!Files.exists(p)) {
                    return new byte[0];
                }
                return Files.readAllBytes(p);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read bytes from file: " + args[0], e);
            }
        }, new FunctionData(
                "readBytes",
                "Reads a file as binary data, or empty byte[] if it does not exist.",
                "readBytes(path:string) -> byte[]"
        ));

        registry.register("fileExists", (args, vars) -> {
            try {
                Path p = Path.of(String.valueOf(args[0]));
                return Files.exists(p);
            } catch (Exception e) {
                return false;
            }
        }, new FunctionData(
                "fileExists",
                "Checks whether a file exists.",
                "fileExists(path:string) -> boolean"
        ));

        registry.register("readBytes", (args, vars) -> {
            Path path = Path.of(args[0].toString());
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + path, e);
            }
        }, new FunctionData("readBytes",
                "Reads a file fully into a byte[]",
                "readBytes(path:string) -> byte[]"));

        registry.register("readText", (args, vars) -> {
            Path path = Path.of(args[0].toString());
            try {
                return Files.readString(path, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + path, e);
            }
        }, new FunctionData("readText",
                "Reads a file into a UTF-8 string",
                "readText(path:string) -> string"));

        // New: downloadBytes(url) -> byte[]
        registry.register("downloadBytes", (args, vars) -> {
            String url = String.valueOf(args[0]);
            try {
                HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                        .timeout(Duration.ofSeconds(30))
                        .header("User-Agent", "Aliva/1.0")
                        .GET()
                        .build();
                HttpResponse<byte[]> resp = HTTP.send(req, HttpResponse.BodyHandlers.ofByteArray());
                int code = resp.statusCode();
                if (code >= 200 && code < 300) {
                    byte[] body = resp.body();
                    return body == null ? new byte[0] : body;
                }
                throw new RuntimeException("downloadBytes HTTP " + code + " for " + url);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("downloadBytes failed for " + url, e);
            }
        }, new FunctionData(
                "downloadBytes",
                "Downloads the resource at a URL and returns its bytes.",
                "downloadBytes(url:string) -> byte[]"
        ));

    }
}
