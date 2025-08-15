package io.github.synte.aliva;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ListAndStringTests {

    @Test @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testJoinAndSplit() {
        String code = """
                list items = ["a", "b", "c"]
                string joined = join(items, ",")
                list splitItems = split(joined, ",")
                print(length(splitItems))
                """;
        assertEquals("3", TestUtils.runScript(code));
    }

    @Test @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testReplaceAndTrim() {
        String code = """
                string text = "  Hello World  "
                string changed = replace(trim(text), "World", "DSL")
                print(changed)
                """;
        assertEquals("Hello DSL", TestUtils.runScript(code));
    }

    @Test @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testConcat() {
        String code = """
                print(concat("Hello", " ", "DSL"))
                """;
        assertEquals("Hello DSL", TestUtils.runScript(code));
    }

    @Test @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testAppendAndGet() {
        String code = """
                list arr = ["a"]
                append(arr, "b")
                print(get(arr, 1))
                """;
        assertEquals("b", TestUtils.runScript(code));
    }

    @Test @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testContains() {
        String code = """
                print(contains("Hello DSL", "DSL"))
                """;
        assertEquals("true", TestUtils.runScript(code));
    }
}