package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class HtmlSelectorFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testHtmlSelectFunctions() {
        String html = """
                <html><body>
                  <div id='a' class='x'>One</div>
                  <div id='b' class='x'>Two <span>Inner</span></div>
                  <a href='https://example.com' data-k='v'>Link</a>
                </body></html>
                """;
        String script = """
                string h = %s
                any doc = fetchLocal(h)

                println(html(doc) != "" ? "ok" : "bad")
                println(selectText(doc, "div.x"))             // One
                println(selectAttr(doc, "a", "href"))         // https://example.com
                println(contains(selectHtml(doc, "div#b"), "span")) // true
                list allText = selectAllText(doc, "div")
                println(length(allText))                       // 2
                list hrefs = selectAllAttr(doc, "a", "data-k")
                println(join(hrefs, ","))                      // v
                list els = selectAll(doc, "div.x")
                println(length(els))                           // 2
                """.formatted(tripleQuote(html));
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("ok", lines.get(0));
        assertEquals("One", lines.get(1));
        assertEquals("https://example.com", lines.get(2));
        assertEquals("true", lines.get(3));
        assertEquals("2", lines.get(4));
        assertEquals("v", lines.get(5));
        assertEquals("2", lines.get(6));
    }

    private static String tripleQuote(String s) {
        // Prepare as a DSL multiline string literal using triple quotes
        String esc = s.replace("\\", "\\\\").replace("\"", "\\\"");
        return "\"\"\"\n" + esc + "\n\"\"\"";
    }
}