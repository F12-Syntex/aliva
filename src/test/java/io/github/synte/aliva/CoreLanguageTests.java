package io.github.synte.aliva;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class CoreLanguageTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testVariableDeclarationAndPrint() {
        String code = """
                string a = "Hello"
                string b = "World"
                print(a, b)
                """;
        assertEquals("Hello World", TestUtils.runScript(code));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testVariableAssignment() {
        String code = """
                string a = "Old"
                a = "New"
                print(a)
                """;
        assertEquals("New", TestUtils.runScript(code));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testIfStatementTrue() {
        String code = """
                string x = "yes"
                if (x == "yes") {
                    print("ok")
                } else {
                    print("fail")
                }
                """;
        assertEquals("ok", TestUtils.runScript(code));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testIfStatementFalse() {
        String code = """
                string x = "no"
                if (x == "yes") {
                    print("ok")
                } else {
                    print("fail")
                }
                """;
        assertEquals("fail", TestUtils.runScript(code));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testForLoopSum() {
        String code = """
                number sum = 0
                for (i in [1, 2, 3]) {
                    sum = sum + i
                }
                print(sum)
                """;
        assertEquals("6", TestUtils.runScript(code));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testWhileLoopIncrement() {
        String code = """
                number i = 0
                while (i < 3) {
                    i = i + 1
                }
                print(i)
                """;
        assertEquals("3", TestUtils.runScript(code));
    }

    @Test
    void testComparisonOperators() {
        String code = """
        print(1 != 2, 2 < 3, 3 <= 3, 4 > 2, 5 >= 5)
        """;
        assertEquals("true true true true true", TestUtils.runScript(code));
    }
}
