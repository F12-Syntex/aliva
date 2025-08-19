package io.github.synte.aliva;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class StringFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testReplaceTrimSplitJoinConcatLowerContainsMatches() {
        String script = """
                println(replace("foobar", "bar", "baz"))  // foobaz
                println(trim("  hi  "))                    // hi
                list parts = split("a,b,,c", ",")
                println(length(parts))                     // 4
                println(join(parts, "|"))                  // a|b||c
                println(concat("ab", 1, null, "Z"))        // ab1nullZ
                println(lower("MiXeD"))                    // mixed
                println(contains("abcdef", "cd"))          // true
                println(matches("a123", "a\\d+"))  // true
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("foobaz", lines.get(0));
        assertEquals("hi", lines.get(1));
        assertEquals("4", lines.get(2));
        assertEquals("a|b||c", lines.get(3));
        assertEquals("ab1nullZ", lines.get(4));
        assertEquals("mixed", lines.get(5));
        assertEquals("true", lines.get(6));
        assertEquals("true", lines.get(7));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testSanitizeReplaceAllUrlSlugGetNumbers() {
        String script = """
                println(sanitizeFilename("a:/b*?c|d<>e"))  // a__b__c_d__e
                println(replaceAll("banana", "na", "NA"))  // baNANA
                println(urlSlug("https://example.com/a/b/c?x=1")) // c
                list nums = getNumbers("a10 b200 c03");
                println(join(nums, ","))                   // 10,200,03
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("a__b__c_d__e", lines.get(0));
        assertEquals("baNANA", lines.get(1));
        assertEquals("c", lines.get(2));
        assertEquals("10,200,03", lines.get(3));
    }
}
