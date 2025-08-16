package io.github.synte.aliva.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.synte.aliva.parser.ScraperDSLBaseVisitor;
import io.github.synte.aliva.parser.ScraperDSLParser;

public class DSLInterpreter extends ScraperDSLBaseVisitor<Object> {

    private final Map<String, Object> variables = new HashMap<>();
    private final FunctionRegistry functions = new FunctionRegistry();

    public DSLInterpreter() {
        CoreFunctions.register(functions);
        HttpFunctions.register(functions);
        HtmlSelectorFunctions.register(functions);
        StringFunctions.register(functions);
        ListFunctions.register(functions);
        FileFunctions.register(functions);
        JsonFunctions.register(functions);
        EpubFunctions.register(functions);
        new BrowserFunctions().register(functions);
        MiscFunctions.register(functions);
    }

    @Override
    public Object visitStatement(ScraperDSLParser.StatementContext ctx) {
        if (ctx.getChildCount() > 0) {
            String first = ctx.getChild(0).getText();
            if ("break".equals(first)) {
                throw new BreakException();
            }
            if ("continue".equals(first)) {
                throw new ContinueException();
            }
        }
        return super.visitStatement(ctx);
    }

    @Override
    public Object visitVarDecl(ScraperDSLParser.VarDeclContext ctx) {
        String type = ctx.getChild(0).getText();
        String name = ctx.ID().getText();
        Object value;
        if (ctx.expression() != null) {
            value = visit(ctx.expression());
        } else {
            value = switch (type) {
                case "string" ->
                    "";
                case "number" ->
                    0.0;
                case "boolean" ->
                    false;
                case "list" ->
                    new ArrayList<>();
                case "map" ->
                    new LinkedHashMap<>();
                default ->
                    null;
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
            variables.put(ctx.ID().getText(), visit(ctx.expression(0)));
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
            try {
                visit(ctx.block());
            } catch (BreakException be) {
                break;
            } catch (ContinueException ce) {
                continue;
            }
            if (--safety <= 0) {
                throw new RuntimeException("Infinite loop detected");
            }
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
                try {
                    visit(ctx.block());
                } catch (BreakException be) {
                    break;
                } catch (ContinueException ce) {
                    continue;
                }
            }
        } else {
            throw new RuntimeException("For loop can only iterate over lists");
        }
        return null;
    }

    @Override
    public Object visitEqualityExpr(ScraperDSLParser.EqualityExprContext ctx) {
        Object left = visit(ctx.comparisonExpr(0));
        for (int i = 1; i < ctx.comparisonExpr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Object right = visit(ctx.comparisonExpr(i));
            left = switch (op) {
                case "==" ->
                    compareEquals(left, right);
                case "!=" ->
                    !compareEquals(left, right);
                default ->
                    throw new RuntimeException("Unknown equality op: " + op);
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
                case "<" ->
                    l < r;
                case "<=" ->
                    l <= r;
                case ">" ->
                    l > r;
                case ">=" ->
                    l >= r;
                default ->
                    throw new RuntimeException("Unknown comparison op: " + op);
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
                case "+" ->
                    (left instanceof String || right instanceof String)
                    ? String.valueOf(left) + String.valueOf(right)
                    : toNumber(left) + toNumber(right);
                case "-" ->
                    toNumber(left) - toNumber(right);
                default ->
                    throw new RuntimeException("Unknown additive op: " + op);
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
                case "*" ->
                    l * r;
                case "/" ->
                    l / r;
                case "%" ->
                    l % r;
                default ->
                    throw new RuntimeException("Unknown multiplicative op: " + op);
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
                case "-" ->
                    -toNumber(val);
                case "!" ->
                    !toBoolean(val);
                default ->
                    throw new RuntimeException("Unknown unary op: " + opText);
            };
        }
        return super.visitUnaryExpr(ctx);
    }

    @Override
    public Object visitLiteral(ScraperDSLParser.LiteralContext ctx) {
        if (ctx.STRING() != null) {
            return stripQuotes(ctx.STRING().getText());
        }
        if (ctx.NUMBER() != null) {
            return Double.parseDouble(ctx.NUMBER().getText());
        }
        if (ctx.BOOLEAN() != null) {
            return Boolean.parseBoolean(ctx.BOOLEAN().getText());
        }
        if (ctx.NULL() != null) {
            return null;
        }
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

    @Override
    public Object visitPrimary(ScraperDSLParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        }
        if (ctx.listLiteral() != null) {
            return visitListLiteral(ctx.listLiteral());
        }
        if (ctx.mapLiteral() != null) {
            return visitMapLiteral(ctx.mapLiteral());
        }
        if (ctx.variableRef() != null) {
            return visitVariableRef(ctx.variableRef());
        }
        if (ctx.funcCall() != null) {
            return visitFuncCall(ctx.funcCall());
        }
        if (ctx.functionLiteral() != null) {
            return visitFunctionLiteral(ctx.functionLiteral());
        }
        if (ctx.expression() != null) {
            return visit(ctx.expression()); // parentheses
        }
        return null;
    }

    public Object visitFunctionLiteral(ScraperDSLParser.FunctionLiteralContext ctx) {
        // Return a Runnable capturing the block's code for deferred execution
        return new DSLRunnable(ctx.block());
    }

    public class DSLRunnable implements Runnable {
        private final ScraperDSLParser.BlockContext blockCtx;

        public DSLRunnable(ScraperDSLParser.BlockContext blockCtx) {
            this.blockCtx = blockCtx;
        }

        @Override
        public void run() {
            try {
                visit(blockCtx);
            } catch (BreakException | ContinueException e) {
                // ignore break/continue in this context
            } catch (Exception e) {
                throw new RuntimeException("Error executing DSLRunnable: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Object visitFuncCall(ScraperDSLParser.FuncCallContext ctx) {
        String name = ctx.ID().getText();
        Object[] args = ctx.expression().stream().map(this::visit).toArray();
        return functions.invoke(name, args, variables);
    }

    private boolean compareEquals(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left instanceof Number && right instanceof Number) {
            return Double.compare(((Number) left).doubleValue(), ((Number) right).doubleValue()) == 0;
        }
        // Normalize to string for comparison (important for DSL map field lookups)
        return String.valueOf(left).equals(String.valueOf(right));
    }

    private double toNumber(Object val) {
        if (val instanceof Number n) {
            return n.doubleValue();
        }
        try {
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            throw new RuntimeException("Expected number but got: " + val);
        }
    }

    private boolean toBoolean(Object value) {
        if (value instanceof Boolean b) {
            return b;
        }
        if (value instanceof Number n) {
            return n.doubleValue() != 0;
        }
        return value != null;
    }

    private String stripQuotes(String s) {
        if (s != null && s.length() >= 2
                && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
