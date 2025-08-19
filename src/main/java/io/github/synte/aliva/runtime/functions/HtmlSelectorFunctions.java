package io.github.synte.aliva.runtime.functions;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class HtmlSelectorFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("html", (args, vars) -> {
            Object v = args[0];
            if (v instanceof Document doc) {
                return doc.outerHtml().trim();
            }
            // If someone passed an Element, return its outer HTML for convenience
            if (v instanceof Element el) {
                return el.outerHtml();
            }
            return String.valueOf(v);
        }, new FunctionData(
            "html",
            "Returns the outer HTML if input is a Document or Element; otherwise stringifies the input.",
            "html(documentOrElementOrValue:any) -> string"
        ));

        registry.register("selectText", (args, vars) -> selectText(args),
            new FunctionData(
                "selectText",
                "Selects the first element matching the selector and returns its text.",
                "selectText(documentOrElement:Document|Element|string, selector:string) -> string"
            ));

        registry.register("selectAttr", (args, vars) -> selectAttr(args),
            new FunctionData(
                "selectAttr",
                "Selects the first element matching the selector and returns an attribute value.",
                "selectAttr(documentOrElement:Document|Element|string, selector:string, attr:string) -> string"
            ));

        registry.register("selectHtml", (args, vars) -> selectHtml(args),
            new FunctionData(
                "selectHtml",
                "Selects the first element matching the selector and returns its outer HTML.",
                "selectHtml(documentOrElement:Document|Element|string, selector:string) -> string"
            ));

        registry.register("selectAllText", (args, vars) -> selectAllText(args),
            new FunctionData(
                "selectAllText",
                "Selects all elements matching the selector and returns their text content.",
                "selectAllText(documentOrElement:Document|Element|string, selector:string) -> list<string>"
            ));

        registry.register("selectAllAttr", (args, vars) -> selectAllAttr(args),
            new FunctionData(
                "selectAllAttr",
                "Selects all elements matching the selector and returns the specified attribute values.",
                "selectAllAttr(documentOrElement:Document|Element|string, selector:string, attr:string) -> list<string>"
            ));

        registry.register("selectAll", (args, vars) -> {
            if (args.length < 2) {
                throw new RuntimeException("selectAll(documentOrElement, selector) requires 2 arguments");
            }
            Object docObj = normalizeDocOrElement(args[0]);
            String selector = String.valueOf(args[1]);

            List<Element> elements;
            if (docObj instanceof Document) {
                elements = ((Document) docObj).select(selector);
            } else if (docObj instanceof Element) {
                elements = ((Element) docObj).select(selector);
            } else {
                throw new RuntimeException("First argument to selectAll must be a Document or Element (or HTML string)");
            }
            return new ArrayList<>(elements);
        }, new FunctionData(
            "selectAll",
            "Returns a list of elements matching the selector.",
            "selectAll(documentOrElement:Document|Element|string, selector:string) -> list<Element>"
        ));
    }

    private static Object normalizeDocOrElement(Object v) {
        if (v instanceof Document || v instanceof Element) {
            return v;
        }
        if (v instanceof String s) {
            // Use parseBodyFragment to preserve fragment structure (like <option>One</option>)
            return Jsoup.parseBodyFragment(s);
        }
        // Fall back: stringify and parse as a body fragment
        return Jsoup.parseBodyFragment(String.valueOf(v));
    }

    private static String selectText(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectText(documentOrElement, selector) requires 2 arguments");
        }
        Object firstArg = normalizeDocOrElement(args[0]);
        String selector = String.valueOf(args[1]);

        Elements els;
        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectText: first argument must be Document or Element (or HTML string)");
        }
        return els.isEmpty() ? "" : els.first().text();
    }

    private static String selectAttr(Object[] args) {
        if (args.length < 3) {
            throw new RuntimeException("selectAttr(documentOrElement, selector, attr) requires 3 arguments");
        }
        Object firstArg = normalizeDocOrElement(args[0]);
        String selector = String.valueOf(args[1]);
        String attr = String.valueOf(args[2]);

        Elements els;
        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAttr: first argument must be Document or Element (or HTML string)");
        }
        return els.isEmpty() ? "" : els.first().attr(attr);
    }

    private static String selectHtml(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectHtml(documentOrElement, selector) requires 2 arguments");
        }
        Object firstArg = normalizeDocOrElement(args[0]);
        String selector = String.valueOf(args[1]);

        Elements els;
        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectHtml: first argument must be Document or Element (or HTML string)");
        }
        return els.isEmpty() ? "" : els.first().outerHtml();
    }

    private static java.util.List<String> selectAllText(Object[] args) {
        if (args.length < 2) {
            throw new RuntimeException("selectAllText(documentOrElement, selector) requires 2 arguments");
        }
        Object firstArg = normalizeDocOrElement(args[0]);
        String selector = String.valueOf(args[1]);

        java.util.List<String> results = new java.util.ArrayList<>();
        Elements els;
        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAllText: first argument must be Document or Element (or HTML string)");
        }
        els.forEach(el -> results.add(el.text()));
        return results;
    }

    private static java.util.List<String> selectAllAttr(Object[] args) {
        if (args.length < 3) {
            throw new RuntimeException("selectAllAttr(documentOrElement, selector, attr) requires 3 arguments");
        }
        Object firstArg = normalizeDocOrElement(args[0]);
        String selector = String.valueOf(args[1]);
        String attr = String.valueOf(args[2]);

        java.util.List<String> results = new java.util.ArrayList<>();
        Elements els;
        if (firstArg instanceof Document) {
            els = ((Document) firstArg).select(selector);
        } else if (firstArg instanceof Element) {
            els = ((Element) firstArg).select(selector);
        } else {
            throw new RuntimeException("selectAllAttr: first argument must be Document or Element (or HTML string)");
        }
        els.forEach(el -> results.add(el.attr(attr)));
        return results;
    }
}