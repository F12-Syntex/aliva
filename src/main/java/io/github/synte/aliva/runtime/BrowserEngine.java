package io.github.synte.aliva.runtime;

public interface BrowserEngine {
    void gotoUrl(String url);
    void click(String selector);
    void type(String selector, String text);
    void waitForSelector(String selector, int timeoutMillis);
    String getContent();
    void close();
}