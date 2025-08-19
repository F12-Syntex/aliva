package io.github.synte.aliva;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.sun.net.httpserver.HttpServer;

public class HttpFunctionsTests {

    private static HttpServer server;

    @BeforeAll
    static void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8081), 0);

        // GET /
        server.createContext("/", exchange -> {
            byte[] resp = "<html><body><p>ok</p></body></html>".getBytes();
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp);
            }
        });

        // POST /post (echoes form param 'q')
        server.createContext("/post", exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes());
            String q = body.replace("q=", "");
            byte[] resp = ("<html><body><p>" + q + "</p></body></html>").getBytes();
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp);
            }
        });

        // GET /text
        server.createContext("/text", exchange -> {
            byte[] resp = "hello".getBytes();
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp);
            }
        });

        // GET /bin
        server.createContext("/bin", exchange -> {
            byte[] resp = new byte[] {1, 2, 3};
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp);
            }
        });

        server.start();
    }

    @AfterAll
    static void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFetchLocal() {
        String html = "<html><body><h1>T</h1></body></html>";
        String script = """
                any d = fetchLocal("%s")
                println(selectText(d, "h1"))
                """.formatted(html);
        String out = TestUtils.runScript(script);
        assertEquals("T", out.trim());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFetchAndFetchPostAndFetchTextAndFetchBytesAndSafeFetch() throws Exception {
        String script = """
                any d = fetch("http://127.0.0.1:8081/")
                println(selectText(d, "p"))

                map form = {"q":"alpha"}
                any p = fetchPost("http://127.0.0.1:8081/post", form)
                println(selectText(p, "p"))

                map hdr = {"Accept":"text/plain"}
                println(fetchText("http://127.0.0.1:8081/text", hdr))

                list bb = fetchBytes("http://127.0.0.1:8081/bin")
                println(length(bb))

                any s = safeFetch("http://127.0.0.1:8081/")
                println(selectText(s, "p"))
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("ok", lines.get(0));
        assertEquals("alpha", lines.get(1));
        assertEquals("hello", lines.get(2));
        assertEquals("3", lines.get(3));
        assertEquals("ok", lines.get(4));
    }
}