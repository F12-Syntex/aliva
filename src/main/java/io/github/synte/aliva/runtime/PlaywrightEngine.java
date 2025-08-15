package io.github.synte.aliva.runtime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PlaywrightEngine implements BrowserEngine {

    private final boolean headless;
    private String lastUrl;

    public PlaywrightEngine(boolean headless) {
        this.headless = headless;
    }

    @Override
    public void gotoUrl(String url) {
        this.lastUrl = url;
        // Removed debug output to keep test output clean
    }

    @Override
    public void click(String selector) {
        // Removed debug output
    }

    @Override
    public void type(String selector, String text) {
        // Removed debug output
    }

    @Override
    public void waitForSelector(String selector, int timeoutMillis) {
        // Removed debug output
    }

    @Override
    public String getContent() {
        try {
            if (lastUrl != null && lastUrl.startsWith("http")) {
                Document doc = Jsoup.connect(lastUrl).get();
                return doc.outerHtml();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "<html></html>";
    }

    @Override
    public void close() {
        // Removed debug output
    }
}