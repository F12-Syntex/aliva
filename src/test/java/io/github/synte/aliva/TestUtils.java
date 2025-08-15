package io.github.synte.aliva;

import io.github.synte.aliva.parser.ScraperDSLLexer;
import io.github.synte.aliva.parser.ScraperDSLParser;
import io.github.synte.aliva.runtime.DSLInterpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    public static String runScript(String code) {
        var lexer = new ScraperDSLLexer(CharStreams.fromString(code));
        var parser = new ScraperDSLParser(new CommonTokenStream(lexer));
        var tree = parser.script();

        DSLInterpreter interpreter = new DSLInterpreter();

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            interpreter.visit(tree);
        } finally {
            System.setOut(originalOut);
        }

        return outputStream.toString().trim();
    }
}