package io.github.synte.aliva.runtime;

import org.jsoup.nodes.Document;

public interface BrowserEngine {
    void gotoUrl(String url);
    void click(String selector);
    void type(String selector, String text);
    void waitForSelector(String selector, int timeoutMs);
    Document getContent();
    void close();
}