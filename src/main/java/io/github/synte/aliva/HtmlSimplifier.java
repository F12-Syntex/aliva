package io.github.synte.aliva;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

public class HtmlSimplifier {

    // Rendered line record used for grouping/compression
    private static class Rendered {
        String fullPath;          // full CSS-like path
        List<String> segments;    // split path segments
        String payload;           // text/attrs like ": Title" or "[src=...]"
        int groupSizeForNote = 1; // if >1, append "(and more like this... count=N)"
    }

    public static class Options {
        public boolean headless = true;
        public String waitForSelector = "body";
        public int waitTimeoutMs = 60000;     // increased to 60s
        public int settleDelayMs = 1200;

        // Cleaning options
        public boolean removeScriptsStyles = true;
        public boolean removeComments = true;
        public boolean removeNoscript = true;
        public boolean removeInlineEventHandlers = true; // onclick, onload, etc.
        public boolean removeTrackingParams = true;      // utm_*, gclid, fbclid

        // Keep id/class for stable selectors; drop only inline styles
        public boolean stripStyleOnly = true;
        public boolean collapseWhitespace = true;
        public boolean removeEmptyTextNodes = true;

        // Output options
        public Path outputRoot = Paths.get("websites");
        public boolean includeHeadMeta = true;

        // Repetition collapsing
        public boolean collapseSimilarSiblings = true;
        public int maxSiblingsPreview = 1;  // only show 1 representative item per similar-group

        // Context compression
        public int minPrefixSegmentsForContext = 8;

        // Parent header grouping
        public boolean enableParentGrouping = true;
        public int minChildrenForParentGroup = 2; // group only if at least this many children

        // Navigation retries
        public int maxNavRetries = 3;
        public int retryBaseDelayMs = 800;

        // Optional per-URL selector overrides
        public Map<String, String> urlWaitSelectorOverrides = new LinkedHashMap<>();
    }

    public static void main(String[] args) throws IOException {
        // Example usage: simplify pornwha pages (homepage, search, series, and a chapter).
        String[] urls = new String[]{
            "https://www.pornhwaz.com/",
            "https://www.pornhwaz.com/?s=her&post_type=wp-manga",
            "https://www.pornhwaz.com/webtoon/benefactors-daughters/",
            "https://www.pornhwaz.com/webtoon/benefactors-daughters/chapter-76/"
        };

        Options opts = new Options();
        // If you want to watch it live for debugging:
        // opts.headless = false;
        simplifyAndSaveAll(urls, opts);
    }

    public static void simplifyAndSaveAll(String[] urls, Options opts) throws IOException {
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions launch = new BrowserType.LaunchOptions().setHeadless(opts.headless);
            Browser browser = playwright.chromium().launch(launch);

            Browser.NewContextOptions ctxOpts = new Browser.NewContextOptions()
                    .setBypassCSP(true)
                    .setIgnoreHTTPSErrors(true)
                    .setUserAgent(realisticUserAgent())
                    .setViewportSize(1280, 900)
                    .setLocale("en-US");
            BrowserContext context = browser.newContext(ctxOpts);

            // Block common ads/trackers to reduce long-running requests
            context.route("**/*", HtmlSimplifier::adBlockRoute);

            for (int i = 0; i < urls.length; i++) {
                try {
                    String referer = (i > 0) ? urls[i - 1] : null;
                    String hydratedHtml = fetchHydratedHtml(context, urls[i], referer, opts);
                    String simplified = simplifyHtml(hydratedHtml, urls[i], opts);
                    Path outPath = outputPathFor(urls[i], opts.outputRoot);
                    Files.createDirectories(outPath.getParent());
                    Files.writeString(outPath, simplified, StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    System.out.println("Saved: " + outPath.toAbsolutePath());
                } catch (Exception e) {
                    System.err.println("Failed to process URL: " + urls[i]);
                    e.printStackTrace();
                }
            }

            context.close();
            browser.close();
        }
    }

    public static String simplifyFromUrl(String url, String referer, Options opts) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions launch = new BrowserType.LaunchOptions().setHeadless(opts.headless);
            Browser browser = playwright.chromium().launch(launch);

            Browser.NewContextOptions ctxOpts = new Browser.NewContextOptions()
                    .setBypassCSP(true)
                    .setIgnoreHTTPSErrors(true)
                    .setUserAgent(realisticUserAgent())
                    .setViewportSize(1280, 900)
                    .setLocale("en-US");
            BrowserContext context = browser.newContext(ctxOpts);
            context.route("**/*", HtmlSimplifier::adBlockRoute);

            String hydratedHtml = fetchHydratedHtml(context, url, referer, opts);
            String simplified = simplifyHtml(hydratedHtml, url, opts);

            context.close();
            browser.close();
            return simplified;
        }
    }

    public static String simplifyFromUrl(String url, Options opts) {
        return simplifyFromUrl(url, null, opts);
    }

    public static String simplifyFromUrl(String url) {
        return simplifyFromUrl(url, null, new Options());
    }

    private static String fetchHydratedHtml(BrowserContext context, String url, String referer, Options opts) {
        Page page = context.newPage();

        Page.NavigateOptions navOpts = new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout((double) opts.waitTimeoutMs);
        if (referer != null && !referer.isEmpty()) {
            navOpts.setReferer(referer);
        }

        String waitSelector = opts.waitForSelector != null ? opts.waitForSelector : "body";
        // Override selector for specific URLs if provided
        for (Map.Entry<String, String> e : opts.urlWaitSelectorOverrides.entrySet()) {
            if (url.equalsIgnoreCase(e.getKey())) {
                waitSelector = e.getValue();
                break;
            }
        }

        navigateWithRetries(page, url, navOpts, opts);

        // Additional wait for LOAD (not networkidle)
        waitForLoadStateSafe(page, LoadState.LOAD, opts.waitTimeoutMs);

        // Try to wait for the selector (text or css). If it fails, continue anyway.
        waitForSelectorSafe(page, waitSelector, opts.waitTimeoutMs);

        if (opts.settleDelayMs > 0) {
            page.waitForTimeout(opts.settleDelayMs);
        }
        String content = page.content();
        page.close();
        return content;
    }

    private static void navigateWithRetries(Page page, String url, Page.NavigateOptions navOpts, Options opts) {
        int attempts = 0;
        Throwable last = null;
        while (attempts < opts.maxNavRetries) {
            try {
                page.navigate(url, navOpts);
                return;
            } catch (Throwable t) {
                last = t;
                attempts++;
                int backoff = opts.retryBaseDelayMs * (int) Math.pow(2, attempts - 1);
                System.err.println("[navigate retry " + attempts + "/" + opts.maxNavRetries + "] " + t.getMessage());
                page.waitForTimeout(backoff);
            }
        }
        if (last instanceof RuntimeException) {
            throw (RuntimeException) last;
        }
        throw new RuntimeException(last);
    }

    private static void waitForLoadStateSafe(Page page, LoadState state, int timeoutMs) {
        try {
            page.waitForLoadState(state, new Page.WaitForLoadStateOptions().setTimeout((double) timeoutMs));
        } catch (Throwable ignore) {
        }
    }

    private static void waitForSelectorSafe(Page page, String selector, int timeoutMs) {
        if (selector == null || selector.isEmpty()) return;
        try {
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout((double) timeoutMs));
        } catch (Throwable t) {
            // Fallback: try 'body' to ensure at least something
            try {
                page.waitForSelector("body", new Page.WaitForSelectorOptions().setTimeout((double) Math.min(3000, timeoutMs)));
            } catch (Throwable ignore) {
            }
        }
    }

    private static void adBlockRoute(Route route) {
        String url = route.request().url();
        String lc = url.toLowerCase();

        // Very lightweight filter to avoid common ad/tracker/long-poll domains
        if (lc.contains("doubleclick.net") ||
            lc.contains("googletagmanager.com") ||
            lc.contains("googlesyndication.com") ||
            lc.contains("adservice") ||
            lc.contains("adsystem") ||
            lc.contains("taboola") ||
            lc.contains("outbrain") ||
            lc.contains("hotjar") ||
            lc.contains("scorecardresearch.com") ||
            lc.contains("connect.facebook.net") ||
            lc.contains("analytics") && !lc.contains("pornhwa.pro") ||
            lc.contains("/prebid/") ||
            lc.contains("adzerk") ||
            lc.contains("moatads") ||
            lc.contains("pubmatic") ||
            lc.contains("cloudfront.net/ads") ||
            lc.contains("pixel.") ||
            lc.contains("/tracking") ||
            lc.contains("/track/")) {
            try {
                route.abort();
                return;
            } catch (Throwable ignore) {
            }
        }

        try {
            route.resume();
        } catch (Throwable ignore) {
        }
    }

    private static String realisticUserAgent() {
        // A modern Chrome-like UA to avoid headless detection heuristics
        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
             + "(KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36";
    }

    public static String simplifyHtml(String rawHtml, String baseUrl, Options opts) {
        if (rawHtml == null || rawHtml.isEmpty()) {
            return "";
        }

        Document doc = Jsoup.parse(rawHtml, baseUrl);

        // 1) Remove noise
        if (opts.removeScriptsStyles) {
            doc.select("script, style, link[rel~=(?i)stylesheet]").forEach(Element::remove);
        }
        if (opts.removeNoscript) {
            doc.select("noscript").forEach(Element::remove);
        }
        if (opts.removeComments) {
            List<Node> toRemove = new ArrayList<>();
            doc.traverse(new NodeVisitorAdapter() {
                @Override
                public void head(Node node, int depth) {
                    for (Node child : node.childNodes()) {
                        if ("#comment".equals(child.nodeName())) {
                            toRemove.add(child);
                        }
                    }
                }
            });
            for (Node n : toRemove) {
                n.remove();
            }
        }

        // 2) Sanitize attributes but KEEP id/class for robust paths
        for (Element el : doc.getAllElements()) {
            // remove inline handlers
            if (opts.removeInlineEventHandlers) {
                List<String> toDrop = new ArrayList<>();
                for (Attribute a : el.attributes()) {
                    if (a.getKey().toLowerCase().startsWith("on")) {
                        toDrop.add(a.getKey());
                    }
                }
                for (String k : toDrop) {
                    el.removeAttr(k);
                }
            }
            // sanitize URLs
            if (el.hasAttr("href")) {
                el.attr("href", cleanUrl(el.attr("href"), opts));
            }
            if (el.hasAttr("src")) {
                el.attr("src", cleanUrl(el.attr("src"), opts));
            }
            // drop style only
            if (opts.stripStyleOnly) {
                el.removeAttr("style");
            }
        }

        // 3) Whitespace normalization
        if (opts.collapseWhitespace) {
            collapseWhitespace(doc.body());
        }
        if (opts.removeEmptyTextNodes) {
            removeEmptyTextNodes(doc.body());
        }

        // 4) Build rendered lines list (head + body) with loop-collapsing
        List<Rendered> lines = new ArrayList<>();

        if (opts.includeHeadMeta) {
            // head title
            String headTitle = doc.title();
            if (headTitle != null && !headTitle.isEmpty()) {
                pushLine(lines, "head > title", ": " + cleanText(headTitle), 1);
            }
            Elements headMeta = doc.select("head meta");
            for (Element m : headMeta) {
                pushLine(lines, cssPath(m), formatMeta(m), 1);
            }
            Elements headLink = doc.select("head link");
            for (Element l : headLink) {
                pushLine(lines, cssPath(l), formatLink(l), 1);
            }
            Elements headBase = doc.select("head base");
            for (Element b : headBase) {
                String href = attrOrEmpty(b, "href");
                if (!href.isEmpty()) {
                    pushLine(lines, cssPath(b), "[href=" + href + "]", 1);
                }
            }
        }

        Element body = doc.body();
        if (body != null) {
            // Traverse and collect rendered lines with sibling collapsing
            outlineNodeCollect(body, lines, opts);
        }

        // 5) Emit with parent grouping first, then context compression
        StringBuilder out = new StringBuilder();
        if (opts.enableParentGrouping) {
            emitWithParentGroupingThenContext(lines, out, opts.minChildrenForParentGroup, opts.minPrefixSegmentsForContext);
        } else {
            emitWithContextCompression(lines, out, opts.minPrefixSegmentsForContext);
        }

        return out.toString().trim();
    }

    // Build output path websites/<host>/<page>.html (query folded into filename)
    private static Path outputPathFor(String url, Path root) {
        try {
            URI u = URI.create(url);
            String host = u.getHost();
            if (host == null || host.isEmpty()) {
                host = "unknown-host";
            }

            String path = u.getPath();
            String filename;
            if (path == null || path.isEmpty() || path.equals("/")) {
                filename = "index";
            } else {
                String[] parts = path.split("/");
                String last = "";
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (parts[i] != null && !parts[i].isEmpty()) {
                        last = parts[i];
                        break;
                    }
                }
                filename = last.isEmpty() ? "index" : last;
            }

            String query = u.getQuery();
            if (query != null && !query.isEmpty()) {
                String q = query;
                if (q.length() > 100) {
                    q = q.substring(0, 100);
                }
                filename = filename + "_" + urlSafe(q);
            }

            if (!filename.toLowerCase().endsWith(".html")) {
                filename = filename + ".html";
            }

            return root.resolve(host).resolve(filename);
        } catch (Exception e) {
            String safe = urlSafe(url);
            if (safe.length() > 120) {
                safe = safe.substring(0, 120);
            }
            return root.resolve("misc").resolve(safe + ".html");
        }
    }

    private static String urlSafe(String s) {
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8).replace("+", "%20");
        } catch (Exception e) {
            return s.replaceAll("[^a-zA-Z0-9._-]+", "_");
        }
    }

    // ===== Traversal and loop-collapsing =====
    // Meaningful elements
    private static final String MEANINGFUL_SEL = String.join(", ",
            // headings/text
            "h1", "h2", "h3", "h4", "h5", "h6",
            "p", "li", "dt", "dd", "th", "td", "blockquote", "pre", "code",
            // inline
            "a", "span", "strong", "em", "b", "i", "u", "s", "small", "mark", "abbr", "q", "cite", "time", "data", "var", "kbd", "sub", "sup",
            // structure
            "ul", "ol", "dl", "figure", "figcaption", "table", "thead", "tbody", "tfoot", "tr", "td", "th", "caption",
            // media
            "img", "picture", "source", "video", "audio", "track", "iframe"
    );

    private static void outlineNodeCollect(Element root, List<Rendered> lines, Options opts) {
        List<Element> children = root.children();
        if (children.isEmpty()) {
            return;
        }

        // Group by similarity key to detect repeated items
        Map<String, List<Element>> groups = new LinkedHashMap<>();
        for (Element c : children) {
            String key = similarityKey(c);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(c);
        }

        for (List<Element> group : groups.values()) {
            int groupSize = group.size();
            int limit = opts.collapseSimilarSiblings ? Math.max(1, opts.maxSiblingsPreview) : groupSize;
            int emitCount = Math.min(groupSize, limit);

            for (int i = 0; i < emitCount; i++) {
                Element el = group.get(i);
                // Emit line for this element if meaningful
                dumpElementIfMeaningfulCollect(el, lines, groupSize);

                // Recurse into its children
                outlineNodeCollect(el, lines, opts);
            }
        }
    }

    private static void dumpElementIfMeaningfulCollect(Element el, List<Rendered> lines, int groupSize) {
        if (!el.is(MEANINGFUL_SEL)) {
            return;
        }

        String path = cssPath(el);
        String tag = el.normalName();

        String payload;
        switch (tag) {
            case "img": {
                String src = attrOrEmpty(el, "src");
                String alt = attrOrEmpty(el, "alt");
                payload = formatImg(src, alt);
                break;
            }
            case "a": {
                String href = attrOrEmpty(el, "href");
                String text = cleanText(el.text());
                payload = formatA(href, text);
                break;
            }
            case "source": {
                String srcset = attrOrEmpty(el, "srcset");
                String type = attrOrEmpty(el, "type");
                payload = formatSource(srcset, type);
                break;
            }
            case "video":
            case "audio": {
                String src = attrOrEmpty(el, "src");
                String text = cleanText(el.text());
                payload = formatMedia(src, text);
                break;
            }
            case "track": {
                String kind = attrOrEmpty(el, "kind");
                String src = attrOrEmpty(el, "src");
                payload = formatTrack(kind, src);
                break;
            }
            case "time":
            case "data": {
                String value = attrOrEmpty(el, "datetime");
                if (value.isEmpty()) {
                    value = attrOrEmpty(el, "value");
                }
                String text = cleanText(el.text());
                payload = formatTimeData(tag, value, text);
                break;
            }
            default: {
                String text = cleanText(el.text());
                if (!text.isEmpty()) {
                    payload = ": " + text;
                } else {
                    payload = ""; // keep node present even if no text, helps context
                }
                break;
            }
        }

        pushLine(lines, path, payload, groupSize);
    }

    private static void pushLine(List<Rendered> lines, String path, String payload, int groupSize) {
        Rendered r = new Rendered();
        r.fullPath = path;
        r.segments = splitPathToSegments(path);
        r.payload = payload == null ? "" : payload;
        r.groupSizeForNote = Math.max(1, groupSize);
        lines.add(r);
    }

    // ===== Similarity / CSS path helpers =====
    private static String similarityKey(Element el) {
        // Tag + sorted classes
        StringBuilder sb = new StringBuilder(el.tagName());
        if (!el.classNames().isEmpty()) {
            List<String> classes = new ArrayList<>(el.classNames());
            Collections.sort(classes);
            sb.append('|');
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    sb.append('.');
                }
                sb.append(classes.get(i));
            }
        }
        return sb.toString();
    }

    private static String cssPath(Element el) {
        if (hasNonEmpty(el.id())) {
            return buildAncestry(el.parent()) + " > " + tagWithIdAndClasses(el);
        }
        Deque<String> parts = new ArrayDeque<>();
        Element cur = el;
        while (cur != null && !"html".equalsIgnoreCase(cur.tagName())) {
            String part = tagWithIdAndClasses(cur);
            if (needsIndex(cur)) {
                int idx = nthOfTypeAmongSimilar(cur);
                part = part + ":nth-of-type(" + idx + ")";
            }
            parts.addFirst(part);
            cur = cur.parent();
            if (cur != null && "body".equalsIgnoreCase(cur.tagName())) {
                parts.addFirst("body");
                break;
            }
        }
        return String.join(" > ", parts);
    }

    private static String buildAncestry(Element parent) {
        if (parent == null) {
            return "body";
        }
        Deque<String> parts = new ArrayDeque<>();
        Element cur = parent;
        while (cur != null && !"html".equalsIgnoreCase(cur.tagName())) {
            String p = tagWithIdAndClasses(cur);
            if (needsIndex(cur)) {
                int idx = nthOfTypeAmongSimilar(cur);
                p = p + ":nth-of-type(" + idx + ")";
            }
            parts.addFirst(p);
            cur = cur.parent();
            if (cur != null && "body".equalsIgnoreCase(cur.tagName())) {
                parts.addFirst("body");
                break;
            }
        }
        return String.join(" > ", parts);
    }

    private static boolean needsIndex(Element el) {
        if (hasNonEmpty(el.id())) {
            return false;
        }
        Elements siblings = el.parent() != null ? el.parent().children() : new Elements();
        String key = tagWithClassesKey(el);
        int same = 0;
        for (Element s : siblings) {
            if (tagWithClassesKey(s).equals(key)) {
                same++;
            }
            if (same > 1) {
                return true;
            }
        }
        return false;
    }

    private static int nthOfTypeAmongSimilar(Element el) {
        if (el.parent() == null) {
            return 1;
        }
        Elements siblings = el.parent().children();
        String key = tagWithClassesKey(el);
        int idx = 0;
        for (Element s : siblings) {
            if (tagWithClassesKey(s).equals(key)) {
                idx++;
            }
            if (s == el) {
                return idx;
            }
        }
        return 1;
    }

    private static String tagWithClassesKey(Element el) {
        StringBuilder sb = new StringBuilder(el.tagName());
        if (!el.classNames().isEmpty()) {
            List<String> classes = new ArrayList<>(el.classNames());
            Collections.sort(classes);
            for (String c : classes) {
                sb.append('.').append(c);
            }
        }
        return sb.toString();
    }

    private static String tagWithIdAndClasses(Element el) {
        StringBuilder sb = new StringBuilder(el.tagName());
        if (hasNonEmpty(el.id())) {
            sb.append('#').append(el.id());
        }
        if (!el.classNames().isEmpty()) {
            List<String> classes = new ArrayList<>(el.classNames());
            Collections.sort(classes);
            for (String c : classes) {
                sb.append('.').append(c);
            }
        }
        return sb.toString();
    }

    private static boolean hasNonEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    private static String attrOrEmpty(Element el, String key) {
        return el.hasAttr(key) ? el.attr(key) : "";
    }

    // ===== Formatting helpers =====
    private static String formatA(String href, String text) {
        StringBuilder s = new StringBuilder();
        if (!href.isEmpty()) {
            s.append("[href=").append(href).append("]");
        }
        if (!text.isEmpty()) {
            s.append(": ").append(text);
        }
        return s.toString();
    }

    private static String formatImg(String src, String alt) {
        StringBuilder s = new StringBuilder();
        if (!src.isEmpty()) {
            s.append("[src=").append(src).append("]");
        }
        if (!alt.isEmpty()) {
            s.append("[alt=").append(cleanText(alt)).append("]");
        }
        return s.toString();
    }

    private static String formatSource(String srcset, String type) {
        StringBuilder s = new StringBuilder();
        if (!srcset.isEmpty()) {
            s.append("[srcset=").append(srcset).append("]");
        }
        if (!type.isEmpty()) {
            s.append("[type=").append(type).append("]");
        }
        return s.toString();
    }

    private static String formatMedia(String src, String text) {
        StringBuilder s = new StringBuilder();
        if (!src.isEmpty()) {
            s.append("[src=").append(src).append("]");
        }
        if (!text.isEmpty()) {
            s.append(": ").append(text);
        }
        return s.toString();
    }

    private static String formatTrack(String kind, String src) {
        StringBuilder s = new StringBuilder();
        if (!kind.isEmpty()) {
            s.append("[kind=").append(kind).append("]");
        }
        if (!src.isEmpty()) {
            s.append("[src=").append(src).append("]");
        }
        return s.toString();
    }

    private static String formatTimeData(String tag, String value, String text) {
        StringBuilder s = new StringBuilder();
        s.append("[").append(tag).append("]");
        if (!value.isEmpty()) {
            s.append("[value=").append(cleanText(value)).append("]");
        }
        if (!text.isEmpty()) {
            s.append(": ").append(cleanText(text));
        }
        return s.toString();
    }

    private static String formatMeta(Element m) {
        String name = attrOrEmpty(m, "name");
        String property = attrOrEmpty(m, "property");
        String content = attrOrEmpty(m, "content");
        StringBuilder s = new StringBuilder();
        s.append("[meta]");
        if (!name.isEmpty()) {
            s.append("[name=").append(cleanText(name)).append("]");
        }
        if (!property.isEmpty()) {
            s.append("[property=").append(cleanText(property)).append("]");
        }
        if (!content.isEmpty()) {
            s.append(": ").append(cleanText(content));
        }
        return s.toString();
    }

    private static String formatLink(Element l) {
        String rel = attrOrEmpty(l, "rel");
        String href = attrOrEmpty(l, "href");
        StringBuilder s = new StringBuilder();
        s.append("[link]");
        if (!rel.isEmpty()) {
            s.append("[rel=").append(cleanText(rel)).append("]");
        }
        if (!href.isEmpty()) {
            s.append("[href=").append(href).append("]");
        }
        return s.toString();
    }

    // ===== Parent grouping then Context compression =====
    private static void emitWithParentGroupingThenContext(List<Rendered> lines, StringBuilder out,
            int minChildrenForParentGroup,
            int minPrefixSegmentsForContext) {
        if (lines.isEmpty()) {
            return;
        }

        // First pass: group consecutive lines that share the same immediate parent path.
        List<Object> blocks = new ArrayList<>();
        int i = 0;
        while (i < lines.size()) {
            Rendered cur = lines.get(i);
            String parentPath = parentPathOf(cur);
            List<Rendered> group = new ArrayList<>();
            group.add(cur);
            int j = i + 1;
            while (j < lines.size()) {
                Rendered nxt = lines.get(j);
                String nxtParent = parentPathOf(nxt);
                if (Objects.equals(parentPath, nxtParent)) {
                    group.add(nxt);
                    j++;
                } else {
                    break;
                }
            }
            if (group.size() >= minChildrenForParentGroup && parentPath != null) {
                blocks.add(new ParentBlock(parentPath, group));
            } else {
                // These will be handled by context compression later as a flat list
                blocks.addAll(group);
            }
            i = j;
        }

        // Emit blocks: for ParentBlock, emit compact child tails; for flat lines, run context compression.
        for (int b = 0; b < blocks.size(); b++) {
            Object blk = blocks.get(b);
            if (blk instanceof ParentBlock) {
                ParentBlock pb = (ParentBlock) blk;
                out.append("PARENT: ").append(pb.parentPath).append("\n");
                for (Rendered r : pb.children) {
                    String tail = lastSegment(r);
                    if (tail == null) {
                        tail = r.fullPath;
                    }
                    out.append(" • ").append(tail).append(r.payload);
                    if (r.groupSizeForNote > 1) {
                        out.append("  (and more like this... count=").append(r.groupSizeForNote).append(")");
                    }
                    out.append("\n");
                }
                out.append("(children of above parent)\n");
            } else {
                // Collect consecutive non-parent blocks (Rendered) and emit via context compression
                List<Rendered> flat = new ArrayList<>();
                int k = b;
                while (k < blocks.size() && !(blocks.get(k) instanceof ParentBlock)) {
                    flat.add((Rendered) blocks.get(k));
                    k++;
                }
                emitWithContextCompression(flat, out, minPrefixSegmentsForContext);
                b = k - 1; // adjust outer loop index
            }
        }
    }

    private static class ParentBlock {
        String parentPath;
        List<Rendered> children;

        ParentBlock(String parentPath, List<Rendered> children) {
            this.parentPath = parentPath;
            this.children = children;
        }
    }

    private static String parentPathOf(Rendered r) {
        if (r == null || r.segments == null || r.segments.size() < 2) {
            return null;
        }
        return String.join(" > ", r.segments.subList(0, r.segments.size() - 1));
    }

    private static String lastSegment(Rendered r) {
        if (r == null || r.segments == null || r.segments.isEmpty()) {
            return null;
        }
        return r.segments.get(r.segments.size() - 1);
    }

    // ===== Context compression (Longest Common Prefix across adjacent lines) =====
    private static void emitWithContextCompression(List<Rendered> lines, StringBuilder out, int minPrefixSegments) {
        if (lines.isEmpty()) {
            return;
        }
        int i = 0;
        while (i < lines.size()) {
            // Try to extend group while shared prefix stays large enough
            int j = i + 1;
            int bestLcp = lines.get(i).segments.size();
            while (j < lines.size()) {
                int lcp = commonPrefixLen(lines, i, j);
                if (lcp < minPrefixSegments) {
                    break;
                }
                bestLcp = Math.min(bestLcp, lcp);
                j++;
            }

            if (j - i >= 2 && bestLcp >= minPrefixSegments) {
                // Emit CONTEXT + tails
                String context = String.join(" > ", lines.get(i).segments.subList(0, bestLcp));
                out.append("CONTEXT: ").append(context).append("\n");
                for (int k = i; k < j; k++) {
                    Rendered r = lines.get(k);
                    String tail = r.segments.size() > bestLcp
                            ? String.join(" > ", r.segments.subList(bestLcp, r.segments.size()))
                            : "(self)";
                    out.append(" - ").append(tail).append(r.payload);
                    if (r.groupSizeForNote > 1) {
                        out.append("  (and more like this... count=").append(r.groupSizeForNote).append(")");
                    }
                    out.append("\n");
                }
                i = j;
            } else {
                // No context compression; emit full line
                Rendered r = lines.get(i);
                out.append(r.fullPath).append(r.payload);
                if (r.groupSizeForNote > 1) {
                    out.append("  (and more like this... count=").append(r.groupSizeForNote).append(")");
                }
                out.append("\n");
                i++;
            }
        }
    }

    private static int commonPrefixLen(List<Rendered> lines, int start, int endExclusive) {
        if (endExclusive <= start) {
            return 0;
        }
        List<String> base = lines.get(start).segments;
        int lcp = base.size();
        for (int idx = start + 1; idx < endExclusive; idx++) {
            List<String> s = lines.get(idx).segments;
            int m = Math.min(lcp, Math.min(base.size(), s.size()));
            int c = 0;
            for (; c < m; c++) {
                if (!base.get(c).equals(s.get(c))) {
                    break;
                }
            }
            lcp = Math.min(lcp, c);
            if (lcp == 0) {
                break;
            }
        }
        return lcp;
    }

    private static List<String> splitPathToSegments(String path) {
        String[] parts = path.split("\\s*>\\s*");
        return Arrays.asList(parts);
    }

    // ===== Utilities =====
    private static void collapseWhitespace(Element root) {
        if (root == null) {
            return;
        }
        root.traverse(new NodeVisitorAdapter() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode tn = (TextNode) node;
                    tn.text(tn.text().replace('\u00A0', ' ')
                            .replaceAll("\\s+", " ")
                            .trim());
                }
            }
        });
    }

    private static void removeEmptyTextNodes(Element root) {
        if (root == null) {
            return;
        }
        List<Node> toRemove = new ArrayList<>();
        root.traverse(new NodeVisitorAdapter() {
            @Override
            public void head(Node node, int depth) {
                for (Node child : node.childNodes()) {
                    if (child instanceof TextNode) {
                        if (((TextNode) child).text().trim().isEmpty()) {
                            toRemove.add(child);
                        }
                    }
                }
            }
        });
        for (Node n : toRemove) {
            n.remove();
        }
    }

    private static String cleanText(String s) {
        if (s == null) {
            return "";
        }
        String t = s.replace('\u00A0', ' ');
        t = t.replaceAll("\\s+", " ").trim();
        return t;
    }

    private static String cleanUrl(String url, Options opts) {
        if (url == null || url.isEmpty()) {
            return url;
        }
        if (!opts.removeTrackingParams) {
            return url;
        }
        try {
            URI u = URI.create(url);
            String scheme = u.getScheme();
            String authority = u.getRawAuthority();
            String path = u.getRawPath();
            String query = u.getRawQuery();
            String fragment = u.getRawFragment();

            if (query == null || query.isEmpty()) {
                return url;
            }

            String[] parts = query.split("&");
            List<String> kept = new ArrayList<>();
            for (String p : parts) {
                String key = p.split("=", 2)[0].toLowerCase();
                if (key.startsWith("utm_") || key.equals("gclid") || key.equals("fbclid")) {
                    continue;
                }
                kept.add(p);
            }
            String newQuery = kept.isEmpty() ? null : String.join("&", kept);
            URI rebuilt = new URI(scheme, authority, path, newQuery, fragment);
            return rebuilt.toString();
        } catch (Exception e) {
            return url; // fallback
        }
    }

    // Minimal adapter for Jsoup NodeVisitor
    private static abstract class NodeVisitorAdapter implements org.jsoup.select.NodeVisitor {
        @Override
        public void head(Node node, int depth) {
        }
        @Override
        public void tail(Node node, int depth) {
        }
    }
}