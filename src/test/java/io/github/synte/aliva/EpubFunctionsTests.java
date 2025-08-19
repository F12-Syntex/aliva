package io.github.synte.aliva;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class EpubFunctionsTests {

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testEpubEndToEnd() throws Exception {
        Path dir = Files.createTempDirectory("aliva_epub_");
        Path outFile = dir.resolve("book.epub");
        String p = outFile.toString().replace("\\", "\\\\");

        // Create a small fake image as bytes by writing to a path then reading back with readBytes
        Path img = dir.resolve("cover.bin");
        byte[] cover = new byte[]{0,1,2,3,4,5,6};
        Files.write(img, cover);
        String imgPath = img.toString().replace("\\", "\\\\");
        String script = """
                // Create book and set metadata
                epubCreate("bk")
                epubMetadata(bk, "Title T", "Author A", "en")

                // Add chapters
                epubAddChapter(bk, "Ch1", "<html xmlns=\\"http://www.w3.org/1999/xhtml\\"><body><p>C1</p></body></html>")
                epubAddTextChapter(bk, "Ch2", "Line1\\nLine2")

                // Add cover and extra image
                list coverBytes = readBytes("%s")
                epubSetCover(bk, coverBytes, "cover.jpg")
                string rn = epubAddImage(bk, "img.dat", coverBytes)
                println(rn)

                // Save
                epubSave(bk, "%s")
                println(fileExists("%s"))
                """.formatted(imgPath, p, p);

        String out = TestUtils.runScript(script);
        List<String> lines = out.lines().toList();
        assertEquals("img.dat", lines.get(0));
        assertEquals("true", lines.get(1));
        assertTrue(Files.size(outFile) > 0, "EPUB file should be non-empty");
    }
}