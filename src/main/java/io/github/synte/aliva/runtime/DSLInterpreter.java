package io.github.synte.aliva.runtime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.github.synte.aliva.parser.ScraperDSLBaseVisitor;
import io.github.synte.aliva.parser.ScraperDSLParser;

public class DSLInterpreter extends ScraperDSLBaseVisitor<Object> {

    private final Map<String, Object> variables = new HashMap<>();

    @Override
    public Object visitVarDecl(ScraperDSLParser.VarDeclContext ctx) {
        String name = ctx.ID().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        variables.put(name, value);
        return null;
    }

    @Override
    public Object visitAssignment(ScraperDSLParser.AssignmentContext ctx) {
        String name = ctx.ID().getText();
        Object value = visit(ctx.expression());
        variables.put(name, value);
        return null;
    }

    @Override
    public Object visitVariableRef(ScraperDSLParser.VariableRefContext ctx) {
        return variables.get(ctx.ID().getText());
    }

    @Override
    public Object visitFuncCall(ScraperDSLParser.FuncCallContext ctx) {
        String name = ctx.ID().getText();
        Object[] args = ctx.expression().stream().map(this::visit).toArray();

        return switch (name) {
            case "print" -> {
                for (int i = 0; i < args.length; i++) {
                    System.out.print(args[i] != null ? args[i] : "null");
                    if (i < args.length - 1) System.out.print(" ");
                }
                System.out.println();
                yield null;
            }
            case "fetch" -> {
                try {
                    String url = stripQuotes(args[0].toString());
                    Document doc = Jsoup.connect(url).get();
                    yield doc;
                } catch (IOException e) {
                    throw new RuntimeException("Failed to fetch URL", e);
                }
            }
            case "html" -> {
                if (args[0] instanceof Document doc) {
                    yield doc.outerHtml();
                } else {
                    yield args[0] != null ? args[0].toString() : "null";
                }
            }
            default -> throw new RuntimeException("Unknown function: " + name);
        };
    }

    @Override
    public Object visitLiteral(ScraperDSLParser.LiteralContext ctx) {
        return stripQuotes(ctx.STRING().getText());
    }

    private String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}