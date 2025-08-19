package io.github.synte.aliva;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ListFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testLengthIndexOfReverseSliceUniqueFlattenRepeat() {
        String script = """
                list xs = ["a","b","a","c"]
                println(length(xs))                 // 4
                println(indexOf(xs, "b"))          // 1
                list rev = reverse(xs)
                println(join(rev, ","))            // c,a,b,a
                list sl = slice(xs, 1, 3)
                println(join(sl, ","))             // b,a
                list un = unique(xs)
                println(join(un, ","))             // a,b,c
                list nested = [[1,2],[3],[4,5]]
                list flat = flatten(nested)
                println(join(flat, ","))           // 1,2,3,4,5
                list rep = repeat("x", 3)
                println(join(rep, ""))             // xxx
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("4", lines.get(0));
        assertEquals("1", lines.get(1));
        assertEquals("c,a,b,a", lines.get(2));
        assertEquals("b,a", lines.get(3));
        assertEquals("a,b,c", lines.get(4));
        assertEquals("1,2,3,4,5", lines.get(5));
        assertEquals("xxx", lines.get(6));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testRangeAndSortBy() {
        String script = """
                list nums = range(3, 7)
                println(join(nums, ",")) // 3,4,5,6,7

                list items = [
                  {"k":"x","v": 10},
                  {"k":"y","v": 2},
                  {"k":"z","v": "15"}
                ]
                list sorted = sortBy(items, "v")
                // Expect 2,10,15
                println(join([get(get(sorted,0),"v"), get(get(sorted,1),"v"), get(get(sorted,2),"v")], ","))
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("3,4,5,6,7", lines.get(0));
        assertEquals("2,10,15", lines.get(1));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testDumpDoesNotThrow() {
        String script = """
                list xs = [{"a":[1,2]}, {"b":3}]
                dump(xs)
                println("ok")
                """;
        String out = TestUtils.runScript(script);
        assertTrue(out.contains("ok"));
    }
}