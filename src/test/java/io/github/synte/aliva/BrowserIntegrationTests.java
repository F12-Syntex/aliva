package io.github.synte.aliva;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BrowserIntegrationTests {

    @Test @Timeout(value = 15, unit = TimeUnit.SECONDS)
    void testBrowserHydration() {
        assumePlaywrightAvailable();

        String code = """
                browser b = browserLaunch("playwright", true)
                browserGoto(b, "https://example.com")
                waitForHydration(b, "h1", 5000)
                doc page = browserContent(b)
                print(selectText(page, "h1"))
                browserClose(b)
                """;
        String output = TestUtils.runScript(code);
        assertEquals("Example Domain", output);
    }

    private void assumePlaywrightAvailable() {
        try {
            Class.forName("com.microsoft.playwright.Playwright");
        } catch (ClassNotFoundException e) {
            assumeTrue(false, "Playwright not available");
        }
    }
}