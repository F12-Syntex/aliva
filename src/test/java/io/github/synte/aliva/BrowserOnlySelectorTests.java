// File: src/test/java/io/github/synte/aliva/BrowserOnlySelectorTests.java
package io.github.synte.aliva;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.sun.net.httpserver.HttpServer;

/**
 * Tests that exercise DOM selection exclusively through the Browser functions.
 * We intentionally avoid fetchLocal/html() and rely on:
 *  - browserLaunch(headless:boolean) -> browser
 *  - browserGoto(browser, url)
 *  - browserWaitForSelector(browser, selector, timeoutMs)
 *  - waitForHydration(browser, selector, timeoutMs)  // if available
 *  - browserType(browser, selector, text)
 *  - browserClick(browser, selector)
 *  - browserScroll(browser, pixels)
 *  - browserContent(browser) -> org.jsoup.nodes.Document
 *  - browserCurrentUrl(browser) -> string
 *  - selectText/selectAttr/selectHtml/selectAllText/selectAllAttr/selectAll against the returned Document
 *  - browserClose(browser)
 */
@Tag("browser")
public class BrowserOnlySelectorTests {

    private static HttpServer server;

    @BeforeAll
    static void startServer() throws Exception {
        String html = """
            <!doctype html>
            <html>
              <head>
                <meta charset="utf-8"/>
                <title>Local Test Page</title>
                <style>
                  body { font-family: sans-serif; }
                  #spacer { height: 1200px; }
                </style>
              </head>
              <body>
                <header>
                  <h1 id='title' class='title cls'>Main Title</h1>
                  <nav>
                    <a id='home' href='/' data-role='nav'>Home</a>
                    <a id='docs' href='/docs' data-role='nav' data-k='v'>Docs</a>
                  </nav>
                </header>
                <main>
                  <section id='s1' class='section x'>
                    <div id='a' class='x'>One</div>
                    <div id='b' class='x'>Two <span>Inner</span></div>
                    <div id='c'>Three</div>
                  </section>
                  <section id='s2' class='section'>
                    <article>
                      <p data-info='alpha'>Paragraph A</p>
                      <p data-info='beta'>Paragraph B</p>
                      <p>Paragraph C</p>
                    </article>
                  </section>
                </main>

                <div id="spacer"></div>

                <section id="interactive">
                  <input id='q'/>
                  <button id='btn'>Go</button>
                  <div id='out'></div>
                </section>

                <script>
                // Minimal interactivity for the test
                document.getElementById('btn').addEventListener('click', function() {
                  document.getElementById('out').textContent =
                    document.getElementById('q').value;
                });
                </script>
              </body>
            </html>
            """;

        server = HttpServer.create(new InetSocketAddress(8083), 0);
        server.createContext("/testpage", exchange -> {
            byte[] resp = html.getBytes();
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp);
            }
        });
        server.start();
    }

    @AfterAll
    static void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    @Timeout(value = 45, unit = TimeUnit.SECONDS)
    void testBrowserSelectors_endToEnd() {
        String script = """
                any b = browserLaunch(true)
                browserGoto(b, "http://127.0.0.1:8083/testpage")

                // Wait for key selectors to ensure DOM ready
                browserWaitForSelector(b, "h1#title", 5000)
                browserWaitForSelector(b, "section#s1 div#b span", 5000)

                // Pull content as a Document and run selectors
                any doc = browserContent(b)

                // 1) Page title text
                println(selectText(doc, "head title"))             // "Local Test Page"

                // 2) First element with class x under #s1 -> "One"
                println(selectText(doc, "#s1 div.x"))               // "One"

                // 3) Attribute on first nav anchor -> "/"
                println(selectAttr(doc, "nav a", "href"))           // "/"

                // 4) Custom data attribute on #docs -> "v"
                println(selectAttr(doc, "a#docs", "data-k"))        // "v"

                // 5) Ensure #b contains a span via selectHtml
                println(contains(selectHtml(doc, "div#b"), "span")) // "true"

                // 6) Collect texts of all divs in #s1 -> 3 items
                list t = selectAllText(doc, "#s1 div")
                println(length(t))                                  // "3"

                // 7) Collect data-role of nav anchors -> "nav,nav"
                list roles = selectAllAttr(doc, "nav a", "data-role")
                println(join(roles, ","))                           // "nav,nav"

                // 8) Count elements with class x (two divs inside #s1: #a, #b)
                list elsX = selectAll(doc, "div.x")
                println(length(elsX))                               // "2"

                // 9) Complex selector with parent > child
                println(selectText(doc, "section.section#s1 > div#b")) // "Two Inner"

                // 10) Non-existing selector fallbacks
                println(selectText(doc, ".does-not-exist") == "" ? "empty" : "not-empty")
                println(selectAttr(doc, "a#unknown", "href") == "" ? "empty" : "not-empty")

                // 11) selectAllAttr includes empty for missing attrs -> "alpha|beta|"
                list pi = selectAllAttr(doc, "section#s2 p", "data-info")
                println(join(pi, "|"))

                // 12) Browser introspection
                println(browserCurrentUrl(b) != "" ? "ok" : "bad")

                browserClose(b)
                """;

        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();

        int i = 0;
        assertEquals("Local Test Page", lines.get(i++)); // 1
        assertEquals("One", lines.get(i++));             // 2
        assertEquals("/", lines.get(i++));               // 3
        assertEquals("v", lines.get(i++));               // 4
        assertEquals("true", lines.get(i++));            // 5
        assertEquals("3", lines.get(i++));               // 6
        assertEquals("nav,nav", lines.get(i++));         // 7
        assertEquals("2", lines.get(i++));               // 8
        assertEquals("Two Inner", lines.get(i++));       // 9
        assertEquals("empty", lines.get(i++));           // 10a
        assertEquals("empty", lines.get(i++));           // 10b
        assertEquals("alpha|beta|", lines.get(i++));     // 11
        assertEquals("ok", lines.get(i++));              // 12

        assertEquals(i, lines.size(), "Unexpected extra output lines: " + lines);
    }

    @Test
    @Timeout(value = 45, unit = TimeUnit.SECONDS)
    void testBrowserInteractions_andSelectors() {
        String script = """
                any b = browserLaunch(true)
                browserGoto(b, "http://127.0.0.1:8083/testpage")

                // Ensure interactive controls are present
                browserWaitForSelector(b, "#q", 5000)
                browserWaitForSelector(b, "#btn", 5000)

                // Interactions: type, click, scroll
                browserType(b, "#q", "Hello World")
                browserClick(b, "#btn")
                browserScroll(b, 400)

                // Get content as Document and validate interactive output
                any doc = browserContent(b)
                println(selectText(doc, "#out")) // should be "Hello World"

                // Also verify other selectors still behave
                println(selectText(doc, "h1#title")) // "Main Title"
                println(selectAttr(doc, "a#docs", "href")) // "/docs"

                browserClose(b)
                """;

        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();

        int i = 0;
        assertEquals("Hello World", lines.get(i++));
        assertEquals("Main Title", lines.get(i++));
        assertEquals("/docs", lines.get(i++));
        assertEquals(i, lines.size(), "Unexpected extra output lines: " + lines);
    }

    @Test
    @Timeout(value = 45, unit = TimeUnit.SECONDS)
    void testExternalPageSmoke_withBrowserOnly() {
        String script = """
                any b = browserLaunch(true)
                browserGoto(b, "https://example.com")
                // Depending on your runtime, one of these is available
                // If waitForHydration is not present, browserWaitForSelector("h1", 5000) is sufficient
                waitForHydration(b, "body", 5000)
                any doc = browserContent(b)
                println(contains(selectText(doc, "h1"), "Example"))
                println(browserCurrentUrl(b) != "" ? "ok" : "bad")
                browserClose(b)
                """;

        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();

        assertEquals("true", lines.get(0));
        assertEquals("ok", lines.get(1));
        assertEquals(2, lines.size(), "Unexpected extra output lines: " + lines);
    }
}