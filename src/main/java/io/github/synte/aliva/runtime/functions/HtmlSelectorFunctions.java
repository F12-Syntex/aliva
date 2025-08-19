// File: src/main/java/io/github/synte/aliva/runtime/functions/HtmlSelectorFunctions.java
package io.github.synte.aliva.runtime.functions;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

/**
 * Selector utilities that operate strictly on org.jsoup.nodes.Document.
 * Make sure you register this class alongside HttpFunctions in your bootstrap.
 */
public class HtmlSelectorFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("html", (args, vars) -> {
            Document doc = asDoc(args[0]);
            return doc.outerHtml();
        }, new FunctionData(
            "html",
            "Returns the full HTML string of the given Document.",
            "html(doc:Document) -> string"
        ));

        registry.register("selectText", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            Element el = doc.selectFirst(selector);
            return el != null ? el.text() : "";
        }, new FunctionData(
            "selectText",
            "Selects the first element by CSS selector and returns its text, or empty string.",
            "selectText(doc:Document, selector:string) -> string"
        ));

        registry.register("selectAttr", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            String attr = String.valueOf(args[2]);
            Element el = doc.selectFirst(selector);
            return (el != null && el.hasAttr(attr)) ? el.attr(attr) : "";
        }, new FunctionData(
            "selectAttr",
            "Selects the first element by CSS selector and returns an attribute value, or empty string.",
            "selectAttr(doc:Document, selector:string, attr:string) -> string"
        ));

        registry.register("selectHtml", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            Element el = doc.selectFirst(selector);
            return el != null ? el.outerHtml() : "";
        }, new FunctionData(
            "selectHtml",
            "Selects the first element by CSS selector and returns its outer HTML, or empty string.",
            "selectHtml(doc:Document, selector:string) -> string"
        ));

        registry.register("selectAllText", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            Elements els = doc.select(selector);
            List<String> out = new ArrayList<>(els.size());
            for (Element e : els) out.add(e.text());
            return out;
        }, new FunctionData(
            "selectAllText",
            "Selects all elements by CSS selector and returns their text content as a list.",
            "selectAllText(doc:Document, selector:string) -> list<string>"
        ));

        registry.register("selectAllAttr", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            String attr = String.valueOf(args[2]);
            Elements els = doc.select(selector);
            List<String> out = new ArrayList<>(els.size());
            for (Element e : els) out.add(e.hasAttr(attr) ? e.attr(attr) : "");
            return out;
        }, new FunctionData(
            "selectAllAttr",
            "Selects all elements by CSS selector and returns the specified attribute values (empty when missing).",
            "selectAllAttr(doc:Document, selector:string, attr:string) -> list<string>"
        ));

        registry.register("selectAll", (args, vars) -> {
            Document doc = asDoc(args[0]);
            String selector = String.valueOf(args[1]);
            Elements els = doc.select(selector);
            return new ArrayList<>(els);
        }, new FunctionData(
            "selectAll",
            "Selects all elements by CSS selector and returns them as a list of elements.",
            "selectAll(doc:Document, selector:string) -> list<Element>"
        ));
    }

    private static Document asDoc(Object obj) {
        if (obj instanceof Document d) return d;
        // Help pinpoint the type issue at runtime if misused
        throw new RuntimeException("HtmlSelectorFunctions expected org.jsoup.nodes.Document, got: " +
                (obj == null ? "null" : obj.getClass().getName()));
    }
}