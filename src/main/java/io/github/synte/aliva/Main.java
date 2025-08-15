package io.github.synte.aliva;

import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import io.github.synte.aliva.parser.ScraperDSLLexer;
import io.github.synte.aliva.parser.ScraperDSLParser;
import io.github.synte.aliva.runtime.DSLInterpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java -jar aliva.jar <script-file>");
            System.exit(1);
        }

        String code = Files.readString(Path.of(args[0]));
        var lexer = new ScraperDSLLexer(CharStreams.fromString(code));
        var parser = new ScraperDSLParser(new CommonTokenStream(lexer));

        var tree = parser.script();
        DSLInterpreter interpreter = new DSLInterpreter();
        interpreter.visit(tree);
    }
}