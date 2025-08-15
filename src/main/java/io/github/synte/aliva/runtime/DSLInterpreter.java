package io.github.synte.aliva.runtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.synte.aliva.parser.ScraperDSLBaseVisitor;
import io.github.synte.aliva.parser.ScraperDSLParser;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DSLInterpreter extends ScraperDSLBaseVisitor<Object> {

    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, BrowserEngine> browsers = new HashMap<>();

    private final OkHttpClient http = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build();

    // --------------------
    // Statements
    // --------------------
    @Override
    public Object visitVarDecl(ScraperDSLParser.VarDeclContext ctx) {
        String type = ctx.getChild(0).getText();
        String name = ctx.ID().getText();
        Object value;

        if (ctx.expression() != null) {
            value = visit(ctx.expression());
        } else {
            value = switch (type) {
                case "string" -> "";
                case "number" -> 0.0;
                case "boolean" -> false;
                case "list" -> new ArrayList<>();
                case "map" -> new LinkedHashMap<>();
                default -> null;
            };
        }
        variables.put(name, value);
        return null;
    }

    @Override
    public Object visitAssignment(ScraperDSLParser.AssignmentContext ctx) {
        if (ctx.expression().size() == 2) {
            String varName = ctx.ID().getText();
            Object container = variables.get(varName);
            Object keyOrIndex = visit(ctx.expression(0));
            Object value = visit(ctx.expression(1));

            if (container instanceof List<?> list && keyOrIndex instanceof Number num) {
                ((List<Object>) list).set(num.intValue(), value);
            } else if (container instanceof Map<?, ?> map) {
                ((Map<String, Object>) map).put(keyOrIndex.toString(), value);
            } else {
                throw new RuntimeException("Invalid indexed assignment to " + varName);
            }
        } else {
            String name = ctx.ID().getText();
            Object value = visit(ctx.expression(0));
            variables.put(name, value);
        }
        return null;
    }

    @Override
    public Object visitVariableRef(ScraperDSLParser.VariableRefContext ctx) {
        Object container = variables.get(ctx.ID().getText());
        if (ctx.expression() != null) {
            Object keyOrIndex = visit(ctx.expression());
            if (container instanceof List<?> list && keyOrIndex instanceof Number num) {
                return list.get(num.intValue());
            } else if (container instanceof Map<?, ?> map) {
                return map.get(keyOrIndex.toString());
            } else {
                throw new RuntimeException("Invalid index/key access on " + ctx.ID().getText());
            }
        }
        return container;
    }

    @Override
    public Object visitIfStatement(ScraperDSLParser.IfStatementContext ctx) {
        if (toBoolean(visit(ctx.expression()))) {
            visit(ctx.block(0));
        } else if (ctx.block().size() > 1) {
            visit(ctx.block(1));
        }
        return null;
    }

    @Override
    public Object visitWhileStatement(ScraperDSLParser.WhileStatementContext ctx) {
        int safety = 10000;
        while (toBoolean(visit(ctx.expression()))) {
            visit(ctx.block());
            if (--safety <= 0)
                throw new RuntimeException("Infinite loop detected");
        }
        return null;
    }

    @Override
    public Object visitForStatement(ScraperDSLParser.ForStatementContext ctx) {
        String varName = ctx.ID().getText();
        Object iterable = visit(ctx.expression());
        if (iterable instanceof List<?> list) {
            for (Object item : list) {
                variables.put(varName, item);
                visit(ctx.block());
            }
        } else {
            throw new RuntimeException("For loop can only iterate over lists");
        }
        return null;
    }

    // --------------------
    // Expressions
    // --------------------
    @Override
    public Object visitEqualityExpr(ScraperDSLParser.EqualityExprContext ctx) {
        Object left = visit(ctx.comparisonExpr(0));
        for (int i = 1; i < ctx.comparisonExpr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Object right = visit(ctx.comparisonExpr(i));
            left = switch (op) {
                case "==" -> compareEquals(left, right);
                case "!=" -> !compareEquals(left, right);
                default -> throw new RuntimeException("Unknown equality op: " + op);
            };
        }
        return left;
    }

    @Override
    public Object visitComparisonExpr(ScraperDSLParser.ComparisonExprContext ctx) {
        Object left = visit(ctx.additiveExpr(0));
        for (int i = 1; i < ctx.additiveExpr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Object right = visit(ctx.additiveExpr(i));
            double l = toNumber(left);
            double r = toNumber(right);
            left = switch (op) {
                case "<" -> l < r;
                case "<=" -> l <= r;
                case ">" -> l > r;
                case ">=" -> l >= r;
                default -> throw new RuntimeException("Unknown comparison op: " + op);
            };
        }
        return left;
    }

    @Override
    public Object visitAdditiveExpr(ScraperDSLParser.AdditiveExprContext ctx) {
        Object left = visit(ctx.multiplicativeExpr(0));
        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Object right = visit(ctx.multiplicativeExpr(i));
            left = switch (op) {
                case "+" -> (left instanceof String || right instanceof String)
                        ? String.valueOf(left) + String.valueOf(right)
                        : toNumber(left) + toNumber(right);
                case "-" -> toNumber(left) - toNumber(right);
                default -> throw new RuntimeException("Unknown additive op: " + op);
            };
        }
        return left;
    }

    @Override
    public Object visitMultiplicativeExpr(ScraperDSLParser.MultiplicativeExprContext ctx) {
        Object left = visit(ctx.unaryExpr(0));
        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Object right = visit(ctx.unaryExpr(i));
            double l = toNumber(left);
            double r = toNumber(right);
            left = switch (op) {
                case "*" -> l * r;
                case "/" -> l / r;
                case "%" -> l % r;
                default -> throw new RuntimeException("Unknown multiplicative op: " + op);
            };
        }
        return left;
    }

    @Override
    public Object visitUnaryExpr(ScraperDSLParser.UnaryExprContext ctx) {
        if (ctx.op != null) {
            String opText = ctx.op.getText();
            Object val = visit(ctx.unaryExpr());
            return switch (opText) {
                case "-" -> -toNumber(val);
                case "!" -> !toBoolean(val);
                default -> throw new RuntimeException("Unknown unary op: " + opText);
            };
        }
        return super.visitUnaryExpr(ctx);
    }

    @Override
    public Object visitLiteral(ScraperDSLParser.LiteralContext ctx) {
        if (ctx.STRING() != null) return stripQuotes(ctx.STRING().getText());
        if (ctx.NUMBER() != null) return Double.parseDouble(ctx.NUMBER().getText());
        if (ctx.BOOLEAN() != null) return Boolean.parseBoolean(ctx.BOOLEAN().getText());
        if (ctx.NULL() != null) return null;
        return null;
    }

    @Override
    public Object visitListLiteral(ScraperDSLParser.ListLiteralContext ctx) {
        List<Object> list = new ArrayList<>();
        ctx.expression().forEach(e -> list.add(visit(e)));
        return list;
    }

    @Override
    public Object visitMapLiteral(ScraperDSLParser.MapLiteralContext ctx) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (ScraperDSLParser.MapEntryContext entryCtx : ctx.mapEntry()) {
            String key = stripQuotes(entryCtx.STRING().getText());
            Object value = visit(entryCtx.expression());
            map.put(key, value);
        }
        return map;
    }

    // --------------------
    // Functions
    // --------------------
    @Override
    public Object visitFuncCall(ScraperDSLParser.FuncCallContext ctx) {
        String name = ctx.ID().getText();
        Object[] args = ctx.expression().stream().map(this::visit).toArray();

        return switch (name) {
            case "print" -> { print(args, false); yield null; }
            case "println" -> { print(args, true); yield null; }
            case "fetch" -> fetchGet(args[0].toString());
            case "fetchPost" -> fetchPost(args[0].toString(), (Map<String, String>) args[1]);
            case "fetchLocal" -> Jsoup.parse(args[0].toString());
            case "html" -> args[0] instanceof Document doc ? doc.outerHtml().trim() : String.valueOf(args[0]);
            case "selectText" -> selectText(args);
            case "selectAttr" -> selectAttr(args);
            case "selectHtml" -> selectHtml(args);
            case "selectAllText" -> selectAllText(args);
            case "selectAllAttr" -> selectAllAttr(args);
            case "replace" -> args[0].toString().replace(args[1].toString(), args[2].toString());
            case "trim" -> args[0].toString().trim();
            case "split" -> Arrays.asList(args[0].toString().split(args[1].toString()));
            case "join" -> String.join(args[1].toString(), castToStringList(args[0]));
            case "concat" -> concat(args);
            case "length" -> (args[0] instanceof List<?> list) ? list.size() : args[0].toString().length();
            case "get" -> ((List<?>) args[0]).get(((Double) args[1]).intValue());
            case "append" -> { ((List<Object>) args[0]).add(args[1]); yield null; }
            case "contains" -> args[0].toString().contains(args[1].toString());
            case "readFile" -> { try { yield Files.readString(Path.of(args[0].toString())); } catch (IOException e) { throw new RuntimeException(e); } }
            case "writeFile" -> { try { Path p = Path.of(args[0].toString()); Files.createDirectories(p.getParent()); Files.writeString(p, args[1].toString()); } catch (IOException e) { throw new RuntimeException(e); } yield null; }
            case "appendFile" -> { try { Path p = Path.of(args[0].toString()); Files.createDirectories(p.getParent()); Files.writeString(p, args[1].toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND); } catch (IOException e) { throw new RuntimeException(e); } yield null; }
            case "sanitizeFilename" -> args[0].toString().replaceAll("[^a-zA-Z0-9-_]", "_").trim();
            case "toJson" -> { try { com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper(); m.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT); yield m.writeValueAsString(args[0]); } catch (Exception e) { throw new RuntimeException(e); } }
            // Browser functions
            case "browserLaunch" -> browserLaunch(args);
            case "browserGoto" -> { getBrowser(args[0]).gotoUrl(args[1].toString()); yield null; }
            case "browserClick" -> { getBrowser(args[0]).click(args[1].toString()); yield null; }
            case "browserType" -> { getBrowser(args[0]).type(args[1].toString(), args[2].toString()); yield null; }
            case "browserWaitForSelector" -> { getBrowser(args[0]).waitForSelector(args[1].toString(), ((Double) args[2]).intValue()); yield null; }
            case "waitForHydration" -> { int timeout = args.length > 2 ? ((Double) args[2]).intValue() : 10000; getBrowser(args[0]).waitForSelector(args[1].toString(), timeout); yield null; }
            case "browserContent" -> getBrowser(args[0]).getContent();
            case "browserClose" -> { getBrowser(args[0]).close(); yield null; }
            default -> throw new RuntimeException("Unknown function: " + name);
        };
    }

    // --------------------
    // Browser helpers
    // --------------------
    private BrowserEngine browserLaunch(Object[] args) {
        boolean headless = true;
        String engine = "playwright";
        if (args.length > 0) engine = args[0].toString();
        if (args.length > 1) headless = Boolean.parseBoolean(args[1].toString());

        BrowserEngine be;
        switch (engine.toLowerCase()) {
            case "playwright" -> be = new PlaywrightEngine(headless);
            default -> throw new RuntimeException("Unknown browser engine: " + engine);
        }
        String id = UUID.randomUUID().toString();
        browsers.put(id, be);
        return be;
    }

    private BrowserEngine getBrowser(Object idObj) {
        if (idObj instanceof String id && browsers.containsKey(id)) {
            return browsers.get(id);
        }
        if (idObj instanceof BrowserEngine be) {
            return be;
        }
        throw new RuntimeException("Invalid browser reference");
    }

    // --------------------
    // Helpers
    // --------------------
    private boolean compareEquals(Object left, Object right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left instanceof Number && right instanceof Number) {
            return Double.compare(((Number) left).doubleValue(), ((Number) right).doubleValue()) == 0;
        }
        return left.toString().equals(right.toString());
    }

    private double toNumber(Object val) {
        if (val instanceof Number n) return n.doubleValue();
        try { return Double.parseDouble(val.toString()); }
        catch (Exception e) { throw new RuntimeException("Expected number but got: " + val); }
    }

    private boolean toBoolean(Object value) {
        if (value instanceof Boolean b) return b;
        if (value instanceof Number n) return n.doubleValue() != 0;
        return value != null;
    }

    private String stripQuotes(String s) {
        if (s != null && s.length() >= 2 &&
                ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    private void print(Object[] args, boolean newline) {
        for (int i = 0; i < args.length; i++) {
            Object val = args[i];
            if (val instanceof Double d && d % 1 == 0) System.out.print((long) d.doubleValue());
            else System.out.print(val != null ? val : "null");
            if (i < args.length - 1) System.out.print(" ");
        }
        if (newline) System.out.println();
    }

    private List<String> castToStringList(Object obj) {
        if (obj instanceof List<?> rawList) {
            List<String> result = new ArrayList<>();
            for (Object o : rawList) result.add(o != null ? o.toString() : "null");
            return result;
        }
        throw new RuntimeException("Expected a list for join()");
    }

    private Document fetchGet(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            try (Response resp = http.newCall(request).execute()) {
                if (!resp.isSuccessful()) throw new IOException("HTTP error: " + resp.code());
                return Jsoup.parse(resp.body().string());
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private Document fetchPost(String url, Map<String, String> params) {
        try {
            FormBody.Builder form = new FormBody.Builder();
            params.forEach(form::add);
            Request request = new Request.Builder().url(url).post(form.build()).build();
            try (Response resp = http.newCall(request).execute()) {
                if (!resp.isSuccessful()) throw new IOException("HTTP error: " + resp.code());
                return Jsoup.parse(resp.body().string());
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private String selectText(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().text();
    }

    private String selectAttr(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().attr(args[2].toString());
    }

    private String selectHtml(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().outerHtml();
    }

    private List<String> selectAllText(Object[] args) {
        List<String> results = new ArrayList<>();
        ((Document) args[0]).select(args[1].toString()).forEach(el -> results.add(el.text()));
        return results;
    }

    private List<String> selectAllAttr(Object[] args) {
        List<String> results = new ArrayList<>();
        ((Document) args[0]).select(args[1].toString()).forEach(el -> results.add(el.attr(args[2].toString())));
        return results;
    }

    private String concat(Object[] args) {
        StringBuilder sb = new StringBuilder();
        for (Object a : args) sb.append(a != null ? a.toString() : "null");
        return sb.toString();
    }
}