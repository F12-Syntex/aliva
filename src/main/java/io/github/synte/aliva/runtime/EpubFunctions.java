package io.github.synte.aliva.runtime;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;

public class EpubFunctions {

    public static void register(FunctionRegistry registry) {

        registry.register("epubCreate", (args, vars) -> {
            Book book = new Book();
            vars.put(args[0].toString(), book);
            return book;
        });

        registry.register("epubMetadata", (args, vars) -> {
            Book book = (Book) args[0];
            String title = args[1].toString();
            String author = args[2].toString();
            String language = args[3].toString();

            Metadata md = book.getMetadata();
            md.addTitle(title);
            md.addAuthor(new Author(author));
            md.setLanguage(language);
            return null;
        });

        registry.register("epubAddChapter", (args, vars) -> {
            Book book = (Book) args[0];
            String title = args[1].toString();
            String htmlContent = args[2].toString();
            String fileName = args.length > 3 ? args[3].toString()
                    : title.replaceAll("\\s+", "_") + ".xhtml";

            try {
                Resource res = new Resource(
                        new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)),
                        fileName
                );
                book.addSection(title, res);
                return null;
            } catch (IOException e) {
                throw new RuntimeException("Failed to add chapter: " + title, e);
            }
        });

        registry.register("epubAddTextChapter", (args, vars) -> {
            Book book = (Book) args[0];
            String title = args[1].toString();
            String text = args[2].toString();
            String fileName = args.length > 3 ? args[3].toString()
                    : title.replaceAll("\\s+", "_") + ".xhtml";

            String htmlWrapped = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>"
                    + title + "</title></head><body><h1>" + title + "</h1><p>"
                    + text.replace("\n", "<br/>") + "</p></body></html>";

            try {
                Resource res = new Resource(
                        new ByteArrayInputStream(htmlWrapped.getBytes(StandardCharsets.UTF_8)),
                        fileName
                );
                book.addSection(title, res);
                return null;
            } catch (IOException e) {
                throw new RuntimeException("Failed to add text chapter: " + title, e);
            }
        });

        registry.register("epubSetCover", (args, vars) -> {
            Book book = (Book) args[0];
            byte[] imgBytes = (byte[]) args[1];
            String imgName = args.length > 2 ? args[2].toString() : "cover.jpg";

            try {
                Resource coverRes = new Resource(new ByteArrayInputStream(imgBytes), imgName);
                book.setCoverImage(coverRes);
                return null;
            } catch (IOException e) {
                throw new RuntimeException("Failed to set cover image", e);
            }
        });

        registry.register("epubSave", (args, vars) -> {
            Book book = (Book) args[0];
            String path = args[1].toString();

            try (FileOutputStream out = new FileOutputStream(path)) {
                new EpubWriter().write(book, out);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save EPUB to " + path, e);
            }
            return null;
        });

        // Improved: Add an image resource to EPUB and return the image name for HTML use
        registry.register("epubAddImage", (args, vars) -> {
            Book book = (Book) args[0];
            String imgName = args[1].toString();
            byte[] imgBytes = (byte[]) args[2];

            try {
                Resource imgRes = new Resource(new ByteArrayInputStream(imgBytes), imgName);
                book.getResources().add(imgRes);
                return imgName;
            } catch (IOException e) {
                throw new RuntimeException("Failed to add image: " + imgName, e);
            }
        });
    }
}