package io.github.synte.aliva.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;

public class BrowserFunctions {

    private final Map<String, BrowserEngine> browsers = new HashMap<>();

    public void register(FunctionRegistry registry) {
        registry.register("browserLaunch", (args, vars) -> {
            boolean headless = args.length > 1 && Boolean.parseBoolean(args[1].toString());
            BrowserEngine be = new PlaywrightEngine(headless);
            String id = UUID.randomUUID().toString();
            browsers.put(id, be);
            return be;
        });
        registry.register("browserGoto", (args, vars) -> {
            getBrowser(args[0]).gotoUrl(args[1].toString());
            return null;
        });
        registry.register("browserClick", (args, vars) -> {
            getBrowser(args[0]).click(args[1].toString());
            return null;
        });
        registry.register("browserType", (args, vars) -> {
            getBrowser(args[0]).type(args[1].toString(), args[2].toString());
            return null;
        });
        registry.register("browserWaitForSelector", (args, vars) -> {
            getBrowser(args[0]).waitForSelector(args[1].toString(), ((Double) args[2]).intValue());
            return null;
        });
        registry.register("waitForHydration", (args, vars) -> {
            int timeout = args.length > 2 ? ((Double) args[2]).intValue() : 10000;
            getBrowser(args[0]).waitForSelector(args[1].toString(), timeout);
            return null;
        });
        registry.register("browserContent", (args, vars) -> Jsoup.parse(getBrowser(args[0]).getContent()));
        registry.register("browserClose", (args, vars) -> {
            getBrowser(args[0]).close();
            return null;
        });
        registry.register("browserScroll", (args, vars) -> {
            Object ref = args[0];
            if (ref instanceof PlaywrightEngine pe) {
                int pixels = ((Number) args[1]).intValue();
                pe.scroll(pixels);
            } else {
                throw new RuntimeException("browserScroll requires a PlaywrightEngine browser reference");
            }
            return null;
        });
    }

    private BrowserEngine getBrowser(Object ref) {
        if (ref instanceof BrowserEngine be) {
            return be;
        }
        throw new RuntimeException("Invalid browser reference");
    }
}
