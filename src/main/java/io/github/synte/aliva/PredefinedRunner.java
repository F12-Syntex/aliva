package io.github.synte.aliva;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import io.github.synte.aliva.parser.ScraperDSLLexer;
import io.github.synte.aliva.parser.ScraperDSLParser;
import io.github.synte.aliva.runtime.DSLInterpreter;

public class PredefinedRunner {

    public static void main(String[] args) throws Exception {
        String scriptsFolder = "scripts";

        List<String> scripts = Files.list(Path.of(scriptsFolder))
                .filter(path -> path.toString().endsWith(".aliva"))
                .map(Path::toString)
                .toList();

        for (String scriptPath : scripts) {
            System.out.println("=== Running: " + scriptPath + " ===");
            runScript(scriptPath);
            System.out.println();
        }
    }

    private static void runScript(String scriptFile) throws Exception {
        String code = Files.readString(Path.of(scriptFile));
        var lexer = new ScraperDSLLexer(CharStreams.fromString(code));
        var parser = new ScraperDSLParser(new CommonTokenStream(lexer));

        var tree = parser.script();
        DSLInterpreter interpreter = new DSLInterpreter();
        interpreter.visit(tree);
    }
}
