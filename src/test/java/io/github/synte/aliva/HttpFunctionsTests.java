package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class HttpFunctionsTests {

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
        // Assumes a local test server running:
        //   GET  http://127.0.0.1:8081/ -> <html><body><p>ok</p></body></html>
        //   POST http://127.0.0.1:8081/post -> echoes form param 'q'
        //   GET  /text -> responds with plain text "hello"
        //   GET  /bin  -> responds with 3-byte payload 0x01 0x02 0x03
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