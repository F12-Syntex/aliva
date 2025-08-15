package io.github.synte.aliva.runtime;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlSelectorFunctions {
    public static void register(FunctionRegistry registry) {
        registry.register("html", (args, vars) ->
                args[0] instanceof Document doc ? doc.outerHtml().trim() : String.valueOf(args[0]));
        registry.register("selectText", (args, vars) -> selectText(args));
        registry.register("selectAttr", (args, vars) -> selectAttr(args));
        registry.register("selectHtml", (args, vars) -> selectHtml(args));
        registry.register("selectAllText", (args, vars) -> selectAllText(args));
        registry.register("selectAllAttr", (args, vars) -> selectAllAttr(args));
    }

    private static String selectText(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().text();
    }

    private static String selectAttr(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().attr(args[2].toString());
    }

    private static String selectHtml(Object[] args) {
        Elements els = ((Document) args[0]).select(args[1].toString());
        return els.isEmpty() ? "" : els.first().outerHtml();
    }

    private static List<String> selectAllText(Object[] args) {
        List<String> results = new ArrayList<>();
        ((Document) args[0]).select(args[1].toString()).forEach(el -> results.add(el.text()));
        return results;
    }

    private static List<String> selectAllAttr(Object[] args) {
        List<String> results = new ArrayList<>();
        ((Document) args[0]).select(args[1].toString()).forEach(el -> results.add(el.attr(args[2].toString())));
        return results;
    }
}