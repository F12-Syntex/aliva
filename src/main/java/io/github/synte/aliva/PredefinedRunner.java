package io.github.synte.aliva;

import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import io.github.synte.aliva.parser.ScraperDSLLexer;
import io.github.synte.aliva.parser.ScraperDSLParser;
import io.github.synte.aliva.runtime.DSLInterpreter;

public class PredefinedRunner {
    public static void main(String[] args) throws Exception {
        // Hardcoded path to a script file
        String scriptPath = "scripts/manhwa.aliva";
        
        // Hardcoded manga URL as argument
        String[] scriptArgs = {"S rank"};
        
        System.out.println("=== Running: " + scriptPath + " with arguments: " + String.join(", ", scriptArgs) + " ===");
        runScript(scriptPath, scriptArgs);
        System.out.println();
    }

    private static void runScript(String scriptFile, String[] scriptArgs) throws Exception {
        String code = Files.readString(Path.of(scriptFile));
        var lexer = new ScraperDSLLexer(CharStreams.fromString(code));
        var parser = new ScraperDSLParser(new CommonTokenStream(lexer));

        var tree = parser.script();
        DSLInterpreter interpreter = new DSLInterpreter();
        interpreter.setScriptArgs(scriptArgs);
        interpreter.visit(tree);
    }
}