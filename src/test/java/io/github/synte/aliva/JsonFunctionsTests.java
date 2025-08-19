package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class JsonFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testToJsonParseJsonJsonToMap() {
        String script = """
                map m = {"a":1,"b":[2,3]}
                string s = toJson(m)
                println(contains(s, "a"))
                println(contains(s, "b"))

                any parsed = parseJson(s)
                println(get(get(parsed, "b"), 1)) // 3

                map mm = jsonToMap("{\\"x\\":10,\\"y\\":\\"z\\"}")
                println(get(mm, "x"))
                println(get(mm, "y"))
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("true", lines.get(0));
        assertEquals("true", lines.get(1));
        assertEquals("3", lines.get(2));
        assertEquals("10", lines.get(3));
        assertEquals("z", lines.get(4));
    }
}