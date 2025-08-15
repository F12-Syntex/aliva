package io.github.synte.aliva.runtime;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

public class PlaywrightEngine implements BrowserEngine {

    private final boolean headless;
    private String lastUrl;
    private Browser browser;
    private Page page;
    private Playwright playwright;

    public PlaywrightEngine(boolean headless) {
        this.headless = headless;
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(headless)
        );
        this.page = browser.newPage();
    }

    @Override
    public void gotoUrl(String url) {
        this.lastUrl = url;
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
    }

    @Override
    public void click(String selector) {
        page.click(selector);
    }

    @Override
    public void type(String selector, String text) {
        page.fill(selector, text);
    }

    @Override
    public void waitForSelector(String selector, int timeoutMillis) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMillis));
    }

    @Override
    public String getContent() {
        String html = page.content();
        // Return the fully hydrated DOM as HTML
        return html;
    }

    @Override
    public void close() {
        try {
            if (page != null) page.close();
        } catch (Exception ignored) {}
        try {
            if (browser != null) browser.close();
        } catch (Exception ignored) {}
        try {
            if (playwright != null) playwright.close();
        } catch (Exception ignored) {}
    }

    // Extra helper for scrolling
    public void scroll(int pixels) {
        page.evaluate("window.scrollBy(0, " + pixels + ");");
    }
}