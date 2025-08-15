package io.github.synte.aliva.runtime;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlSelectorFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("html", (args, vars)
                -> args[0] instanceof Document doc ? doc.outerHtml().trim() : String.valueOf(args[0]));

        registry.register("selectText", (args, vars) -> selectText(args));
        registry.register("selectAttr", (args, vars) -> selectAttr(args));
        registry.register("selectHtml", (args, vars) -> selectHtml(args));
        registry.register("selectAllText", (args, vars) -> selectAllText(args));
        registry.register("selectAllAttr", (args, vars) -> selectAllAttr(args));

        registry.register("selectAll", (args, vars) -> {
            if (args.length < 2) {
                throw new RuntimeException("selectAll(documentOrElement, selector) requires 2 arguments");
            }

            Object docObj = args[0];
            if (!(docObj instanceof Document) && !(docObj instanceof Element)) {
                throw new RuntimeException("First argument to selectAll must be a Document or Element");
            }

            String selector = String.valueOf(args[1]);
            List<Element> elements;

            if (docObj instanceof Document) {
                elements = ((Document) docObj).select(selector);
            } else {
                elements = ((Element) docObj).select(selector);
            }

            // Return as a DSL list
            return new ArrayList<>(elements);
        });
    }

    // === Updated to support both Document and Element ===
    private static String selectText(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectText(documentOrElement, selector) requires 2 arguments");
        }

        Object firstArg = args[0];
        String selector = String.valueOf(args[1]);
        Elements els;

        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectText: first argument must be Document or Element");
        }

        return els.isEmpty() ? "" : els.first().text();
    }

    private static String selectAttr(Object[] args) {
        if (args.length < 3) {
            throw new RuntimeException("selectAttr(documentOrElement, selector, attr) requires 3 arguments");
        }

        Object firstArg = args[0];
        String selector = String.valueOf(args[1]);
        String attr = String.valueOf(args[2]);
        Elements els;

        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAttr: first argument must be Document or Element");
        }

        return els.isEmpty() ? "" : els.first().attr(attr);
    }

    private static String selectHtml(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectHtml(documentOrElement, selector) requires 2 arguments");
        }

        Object firstArg = args[0];
        String selector = String.valueOf(args[1]);
        Elements els;

        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectHtml: first argument must be Document or Element");
        }

        return els.isEmpty() ? "" : els.first().outerHtml();
    }

    private static List<String> selectAllText(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectAllText(documentOrElement, selector) requires 2 arguments");
        }

        Object firstArg = args[0];
        String selector = String.valueOf(args[1]);
        List<String> results = new ArrayList<>();
        Elements els;

        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAllText: first argument must be Document or Element");
        }

        els.forEach(el -> results.add(el.text()));
        return results;
    }

    private static List<String> selectAllAttr(Object[] args) {
        if (args.length < 3) {
            throw new RuntimeException("selectAllAttr(documentOrElement, selector, attr) requires 3 arguments");
        }

        Object firstArg = args[0];
        String selector = String.valueOf(args[1]);
        String attr = String.valueOf(args[2]);
        List<String> results = new ArrayList<>();
        Elements els;

        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAllAttr: first argument must be Document or Element");
        }

        els.forEach(el -> results.add(el.attr(attr)));
        return results;
    }
}