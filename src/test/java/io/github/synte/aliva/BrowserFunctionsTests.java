package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Tag("browser")
@Disabled("Enable when Playwright and browsers are available in CI environment")
public class BrowserFunctionsTests {

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testBrowserWorkflow() {
        String script = """
                any b = browserLaunch(true)
                browserGoto(b, "https://example.com")
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
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testBrowserScrollAndTypeAndClick() {
        String html = """
            <html><body>
                <input id='q'/>
                <button id='btn'>Go</button>
                <div id='out'></div>
                <script>
                document.getElementById('btn').addEventListener('click', function() {
                  document.getElementById('out').textContent = document.getElementById('q').value;
                });
                </script>
            </body></html>
            """;
        // Requires a local server serving this page; otherwise disable this test or adapt to a known page.
        String script = """
                any b = browserLaunch(true)
                browserGoto(b, "http://127.0.0.1:8082/testpage")
                browserWaitForSelector(b, "#q", 5000)
                browserType(b, "#q", "Hello")
                browserClick(b, "#btn")
                browserScroll(b, 200)
                any doc = browserContent(b)
                println(selectText(doc, "#out"))
                browserClose(b)
                """;
        String out = TestUtils.runScript(script);
        assertEquals("Hello", out.trim());
    }
}