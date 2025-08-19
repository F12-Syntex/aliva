package io.github.synte.aliva.runtime.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;

import io.github.synte.aliva.runtime.BrowserEngine;
import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;
import io.github.synte.aliva.runtime.PlaywrightEngine;

public class BrowserFunctions {

    private final Map<String, BrowserEngine> browsers = new HashMap<>();

    public void register(FunctionRegistry registry) {
        registry.register("browserLaunch", (args, vars) -> {
            boolean headless = args.length > 1 && Boolean.parseBoolean(args[1].toString());
            BrowserEngine be = new PlaywrightEngine(headless);
            String id = UUID.randomUUID().toString();
            browsers.put(id, be);
            return be;
        }, new FunctionData(
            "browserLaunch",
            "Launches a new browser instance with optional headless mode and returns the browser reference.",
            "browserLaunch([headless:boolean]) -> BrowserEngine"
        ));

        registry.register("browserGoto", (args, vars) -> {
            getBrowser(args[0]).gotoUrl(args[1].toString());
            return null;
        }, new FunctionData(
            "browserGoto",
            "Navigates the browser to a specified URL.",
            "browserGoto(browser:BrowserEngine, url:string)"
        ));

        registry.register("browserClick", (args, vars) -> {
            getBrowser(args[0]).click(args[1].toString());
            return null;
        }, new FunctionData(
            "browserClick",
            "Clicks an element matching the selector.",
            "browserClick(browser:BrowserEngine, selector:string)"
        ));

        registry.register("browserType", (args, vars) -> {
            getBrowser(args[0]).type(args[1].toString(), args[2].toString());
            return null;
        }, new FunctionData(
            "browserType",
            "Types text into an element matching the selector.",
            "browserType(browser:BrowserEngine, selector:string, text:string)"
        ));

        registry.register("browserWaitForSelector", (args, vars) -> {
            getBrowser(args[0]).waitForSelector(args[1].toString(), ((Number) args[2]).intValue());
            return null;
        }, new FunctionData(
            "browserWaitForSelector",
            "Waits for an element matching the selector within the timeout.",
            "browserWaitForSelector(browser:BrowserEngine, selector:string, timeoutMs:number)"
        ));

        registry.register("waitForHydration", (args, vars) -> {
            int timeout = args.length > 2 ? ((Number) args[2]).intValue() : 10000;
            getBrowser(args[0]).waitForSelector(args[1].toString(), timeout);
            return null;
        }, new FunctionData(
            "waitForHydration",
            "Waits for a selector indicating client-side hydration to complete.",
            "waitForHydration(browser:BrowserEngine, selector:string, [timeoutMs:number])"
        ));

        registry.register("browserContent", (args, vars) -> Jsoup.parse(getBrowser(args[0]).getContent()), new FunctionData(
            "browserContent",
            "Returns the current page content parsed as HTML Document.",
            "browserContent(browser:BrowserEngine) -> Document"
        ));

        registry.register("browserClose", (args, vars) -> {
            getBrowser(args[0]).close();
            return null;
        }, new FunctionData(
            "browserClose",
            "Closes the browser and releases resources.",
            "browserClose(browser:BrowserEngine)"
        ));

        registry.register("browserScroll", (args, vars) -> {
            Object ref = args[0];
            if (ref instanceof PlaywrightEngine pe) {
                int pixels = ((Number) args[1]).intValue();
                pe.scroll(pixels);
            } else {
                throw new RuntimeException("browserScroll requires a PlaywrightEngine browser reference");
            }
            return null;
        }, new FunctionData(
            "browserScroll",
            "Scrolls the page vertically by a number of pixels.",
            "browserScroll(browser:PlaywrightEngine, pixels:number)"
        ));

        registry.register("browserCurrentUrl", (args, vars) -> {
            return getBrowser(args[0]).getCurrentUrl();
        }, new FunctionData(
            "browserCurrentUrl",
            "Returns the current page URL.",
            "browserCurrentUrl(browser:BrowserEngine) -> string"
        ));
    }

    private BrowserEngine getBrowser(Object ref) {
        if (ref instanceof BrowserEngine be) {
            return be;
        }
        throw new RuntimeException("Invalid browser reference");
    }
}