package io.github.synte.aliva.runtime.functions;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;
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
        }, new FunctionData(
            "epubCreate",
            "Creates a new EPUB Book and stores it into variables by a given name.",
            "epubCreate(varName:string) -> Book"
        ));

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
        }, new FunctionData(
            "epubMetadata",
            "Sets title, author and language metadata on the EPUB.",
            "epubMetadata(book:Book, title:string, author:string, language:string)"
        ));

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
        }, new FunctionData(
            "epubAddChapter",
            "Adds an HTML chapter to the EPUB.",
            "epubAddChapter(book:Book, title:string, htmlContent:string, [fileName:string])"
        ));

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
        }, new FunctionData(
            "epubAddTextChapter",
            "Wraps plain text into minimal HTML and adds it as a chapter.",
            "epubAddTextChapter(book:Book, title:string, text:string, [fileName:string])"
        ));

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
        }, new FunctionData(
            "epubSetCover",
            "Sets the cover image for the EPUB.",
            "epubSetCover(book:Book, imageBytes:byte[], [imageName:string])"
        ));

        registry.register("epubSave", (args, vars) -> {
            Book book = (Book) args[0];
            String path = args[1].toString();
            try (FileOutputStream out = new FileOutputStream(path)) {
                new EpubWriter().write(book, out);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save EPUB to " + path, e);
            }
            return null;
        }, new FunctionData(
            "epubSave",
            "Saves the EPUB to a file path.",
            "epubSave(book:Book, filePath:string)"
        ));

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
        }, new FunctionData(
            "epubAddImage",
            "Adds an image resource to the EPUB and returns the resource name.",
            "epubAddImage(book:Book, imageName:string, imageBytes:byte[]) -> string"
        ));
    }
}