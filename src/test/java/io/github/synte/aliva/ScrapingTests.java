package io.github.synte.aliva;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ScrapingTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFetchLocalAndSelectors() {
        String html = "<html><body><h1>Hello</h1><a href='link.html'>Go</a><p>One</p><p>Two</p></body></html>";
        String code = """
                doc page = fetchLocal(\"""%s\""\")
                print(
                    selectText(page, "h1"),
                    selectAttr(page, "a", "href"),
                    length(selectAllText(page, "p")),
                    html(page)
                )
                """.formatted(html);
        String output = TestUtils.runScript(code);
        assertTrue(output.contains("<html"), "Output should contain HTML content");
    }
}
