package io.github.synte.aliva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BareBonesTests {

    @Test
    void testPrintString() {
        String code = """
                string message = "Hello, Aliva!"
                print(message)
                """;
        String output = TestUtils.runScript(code);
        assertEquals("Hello, Aliva!", output);
    }

    @Test
    void testVariableAssignment() {
        String code = """
                string a = "Test"
                string b = "ing"
                print(a, b)
                """;
        String output = TestUtils.runScript(code);
        assertEquals("Test ing", output);
    }

    @Test
    void testFetchHtml() {
        String code = """
                string url = "https://example.com"
                doc page = fetch(url)
                print(html(page))
                """;
        String output = TestUtils.runScript(code);
        assertTrue(output.contains("<html"), "Output should contain HTML");
        assertTrue(output.contains("Example Domain"), "Should contain example.com text");
    }
}