package io.github.synte.aliva;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MiscFunctionsTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testToNumberRandomSleepFormatNumber() {
        String script = """
                println(toNumber(5))           // 5
                println(toNumber("6.25"))      // 6.25
                // Random in [0,1)
                number r = random()
                println((r >= 0 && r < 1) ? "ok" : "bad")
                // Sleep 10ms
                sleep(10)
                println(formatNumber(12345.678, "#,##0.00"))
                """;
        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("5", lines.get(0));
        assertEquals("6.25", lines.get(1));
        assertEquals("ok", lines.get(2));
        assertEquals("12,345.68", lines.get(3));
    }
}