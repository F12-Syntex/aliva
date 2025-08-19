package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class GrammarAndExpressionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testVariablesAssignmentIndexingAndMapListLiterals() {
        String script = """
                number x = 10
                x = x + 5
                println(x)              // 15

                list xs = [1,2,3]
                println(get(xs, 0))          // 1
                xs[1] = 7
                println(xs[1])          // 7

                map m = {"a": 1, "b": 2}
                println(m["a"])         // 1

                // function call and nested indexing
                list ys = reverse(xs)
                println(ys[0])          // 3
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("15", lines.get(0));
        assertEquals("1", lines.get(1));
        assertEquals("7", lines.get(2));
        assertEquals("1", lines.get(3));
        assertEquals("3", lines.get(4));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testIfWhileForBreakContinueTernary() {
        String script = """
                number sum = 0
                // while loop
                number i = 0
                while (i < 5) {
                  if (i == 3) {
                    i = i + 1
                    continue
                  }
                  sum = sum + i
                  i = i + 1
                }
                println(sum) // 0+1+2+4 = 7

                // for-in over list
                list xs = [1,2,3]
                number prod = 1
                for (n in xs) {
                  if (n == 2) { continue }
                  prod = prod * n
                }
                println(prod) // 3

                // break demo
                number j = 0
                number s2 = 0
                while (true) {
                  j = j + 1
                  s2 = s2 + j
                  if (j >= 3) { break }
                }
                println(s2) // 6

                // ternary
                println((sum > 5) ? "gt" : "le")
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("7", lines.get(0));
        assertEquals("3", lines.get(1));
        assertEquals("6", lines.get(2));
        assertEquals("gt", lines.get(3));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testLogicalArithmeticComparison() {
        String script = """
                println( (1 + 2 * 3) )           // 7
                println( (10 / 2 - 1) )          // 4
                println( (5 % 2) )               // 1
                println( (3 > 2 && 1 < 2) )      // true
                println( (3 == 3 || 4 != 4) )    // true
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("7", lines.get(0));
        assertEquals("4", lines.get(1));
        assertEquals("1", lines.get(2));
        assertEquals("true", lines.get(3));
        assertEquals("true", lines.get(4));
    }
}