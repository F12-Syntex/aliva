package io.github.synte.aliva.runtime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightEngine implements BrowserEngine {
    private final Playwright playwright;
    private final Browser browser;
    private final Page page;

    public PlaywrightEngine(boolean headless) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        page = browser.newPage();
    }

    @Override
    public void gotoUrl(String url) {
        page.navigate(url);
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
    public void waitForSelector(String selector, int timeoutMs) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs));
    }

    @Override
    public Document getContent() {
        return Jsoup.parse(page.content());
    }

    @Override
    public void close() {
        browser.close();
        playwright.close();
    }
}