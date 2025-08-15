package io.github.synte.aliva;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class FileIOTests {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testWriteFileCreatesDirectories() throws Exception {
        Path tempDir = Files.createTempDirectory("aliva_test_");
        Path nestedFile = tempDir.resolve("subdir1/subdir2/test.txt");

        String script = """
                string path = "%s"
                writeFile(path, "Hello World")
                """.formatted(nestedFile.toString().replace("\\", "\\\\"));

        String output = TestUtils.runScript(script);

        assertTrue(Files.exists(nestedFile), "File should be created");
        assertEquals("Hello World", Files.readString(nestedFile));
    }
}