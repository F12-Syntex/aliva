package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class CoreFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testPrintAndPrintlnFormatting() {
        String script = """
                println("A", 1, 2.0, null)
                print("X"); print("Y"); println("Z")
                """;
        String out = TestUtils.runScript(script);
        // Note: CoreFunctions.doPrint prints space-separated arguments; numbers like 2.0 -> "2" (integer cast)
        List<String> lines = out.lines().toList();
        assertEquals("A 1 2 null", lines.get(0));
        assertEquals("XYZ", lines.get(1));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testGetSetAppendIsListIsMapClassOfToString() {
        String script = """
                // Create a list and a map
                list xs = [1, 2, 3]
                map m = {"a": 10, "b": 20}

                // get
                println(get(xs, 1))         // expect 2
                println(get(m, "b"))        // expect 20

                // set (list and map)
                set(xs, 1, 42)
                set(m, "b", 99)
                println(get(xs, 1))         // expect 42
                println(get(m, "b"))        // expect 99

                // append
                append(xs, 7)
                println(length(xs))         // expect 4

                // type checks
                println(isList(xs))
                println(isMap(xs))
                println(isMap(m))

                // classOf and toString
                println(classOf(xs) != "" ? "ok" : "bad")
                println(toString(null))
                """;

        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("2", lines.get(0));
        assertEquals("20", lines.get(1));
        assertEquals("42", lines.get(2));
        assertEquals("99", lines.get(3));
        assertEquals("4", lines.get(4));
        assertEquals("true", lines.get(5));
        assertEquals("false", lines.get(6));
        assertEquals("true", lines.get(7));
        assertEquals("ok", lines.get(8));
    }
}
