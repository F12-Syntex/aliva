package io.github.synte.aliva.docgen;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.synte.aliva.runtime.FunctionRegistry;
import io.github.synte.aliva.runtime.functions.DSLFunction;

public class DocumentationGeneratorMain {

    public static void main(String[] args) throws Exception {
        FunctionRegistry registry = new FunctionRegistry();

        String functionsPackage = "io.github.synte.aliva.runtime.functions";
        Set<Class<?>> functionClasses = getClasses(functionsPackage);

        for (Class<?> clazz : functionClasses) {
            Method registerMethod = findRegisterMethod(clazz);
            if (registerMethod != null) {
                if (Modifier.isStatic(registerMethod.getModifiers())) {
                    registerMethod.invoke(null, registry);
                } else {
                    Object instance = tryInstantiate(clazz);
                    if (instance != null) {
                        registerMethod.invoke(instance, registry);
                    }
                }
            }
        }

        // Adjust accessor if needed
        Map<String, DSLFunction> all = registry.getAll();

        String grammarPath = "src/main/antlr4/io/github/synte/aliva/parser/ScraperDSL.g4";
        String grammar = readFileIfExists(grammarPath);

        StringBuilder md = new StringBuilder();
        String ts = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

        md.append("# Aliva Language Reference\n\n");
        md.append("_Generated ").append(ts).append("_\n\n");

        md.append("## Language Specification\n\n");
        if (grammar != null && !grammar.isBlank()) {
            md.append("<details>\n");
            md.append("<summary>ScraperDSL.g4 (click to expand)</summary>\n\n");
            md.append("```antlr\n");
            md.append(grammar);
            md.append("\n```\n");
            md.append("</details>\n\n");
        } else {
            md.append("_Grammar file not found at ").append(grammarPath).append("._\n\n");
        }

        md.append("## Functions\n\n");

        all.entrySet().stream()
            .map(e -> e.getValue())
            .filter(dsl -> dsl != null && dsl.getMetadata() != null)
            .map(dsl -> dsl.getMetadata())
            .sorted((a, b) -> safe(a.getName()).compareToIgnoreCase(safe(b.getName())))
            .forEach(meta -> {
                String usage = safe(meta.getUsage());
                String desc = safe(meta.getDescription());
                if (usage.isBlank()) {
                    // Fallback: show name when usage is missing
                    usage = meta.getName() != null ? meta.getName() : "";
                }
                // Single compact bullet line
                md.append("- `").append(usage).append("`");
                if (!desc.isBlank()) {
                    md.append(" : ").append(desc);
                }
                md.append("\n");
            });

        Path out = Path.of("docs", "LANGUAGE_REFERENCE.md");
        Files.createDirectories(out.getParent());
        try (FileWriter writer = new FileWriter(out.toFile(), StandardCharsets.UTF_8)) {
            writer.write(md.toString());
        }
        System.out.println("Documentation generated: " + out.toAbsolutePath());
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private static Method findRegisterMethod(Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (!m.getName().equals("register")) continue;
            if (m.getParameterCount() != 1) continue;
            if (!m.getParameterTypes()[0].getName().equals("io.github.synte.aliva.runtime.FunctionRegistry")) continue;
            if (!Modifier.isPublic(m.getModifiers())) continue;
            return m;
        }
        return null;
    }

    private static Object tryInstantiate(Class<?> clazz) {
        try {
            Constructor<?> ctor = clazz.getDeclaredConstructor();
            if (!Modifier.isPublic(ctor.getModifiers())) {
                ctor.setAccessible(true);
            }
            return ctor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private static Set<Class<?>> getClasses(String packageName) throws Exception {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

    private static String readFileIfExists(String path) {
        try {
            Path p = Path.of(path);
            if (!Files.exists(p)) return null;
            return Files.readString(p, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}