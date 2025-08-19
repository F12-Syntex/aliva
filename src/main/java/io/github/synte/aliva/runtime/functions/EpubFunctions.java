package io.github.synte.aliva.runtime.functions;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class EpubFunctions {

    public static void register(FunctionRegistry registry) {

        // Create a new book and bind to a variable name
        registry.register("epubCreate", (args, vars) -> {
            Book book = new Book();
            vars.put(args[0].toString(), book);
            return book;
        }, new FunctionData("epubCreate", "Create book", "epubCreate(name)"));

        // Set metadata: title, author, lang
        registry.register("epubMetadata", (args, vars) -> {
            Book b = (Book) args[0];
            b.getMetadata().addTitle(args[1].toString());
            b.getMetadata().addAuthor(new Author(args[2].toString()));
            b.getMetadata().setLanguage(args[3].toString());
            return null;
        }, new FunctionData("epubMetadata", "Set metadata", "epubMetadata(book,title,author,lang)"));

        // Add HTML chapter
        registry.register("epubAddChapter", (args, vars) -> {
            Book b = (Book) args[0];
            String title = args[1].toString();
            String html = args[2].toString();
            Resource r = new Resource(html.getBytes(StandardCharsets.UTF_8), title + ".xhtml");
            r.setMediaType(MediatypeService.XHTML);
            b.addSection(title, r);
            return null;
        }, new FunctionData("epubAddChapter", "Add chapter", "epubAddChapter(book,title,html)"));

        // Add plain text chapter (wrap in minimal HTML)
        registry.register("epubAddTextChapter", (args, vars) -> {
            Book b = (Book) args[0];
            String title = args[1].toString();
            String text = args[2].toString().replace("\n", "<br/>");
            String html = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><body>" + text + "</body></html>";
            Resource r = new Resource(html.getBytes(StandardCharsets.UTF_8), title + ".xhtml");
            r.setMediaType(MediatypeService.XHTML);
            b.addSection(title, r);
            return null;
        }, new FunctionData("epubAddTextChapter", "Add text chapter", "epubAddTextChapter(book,title,text)"));

        // Set cover image
        registry.register("epubSetCover", (args, vars) -> {
            Book b = (Book) args[0];
            byte[] img = (byte[]) args[1];
            String name = args.length > 2 ? args[2].toString() : "cover.jpg";
            try {
                Resource r = new Resource(new ByteArrayInputStream(img), name);
                r.setMediaType(MediatypeService.JPG);
                b.setCoverImage(r);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData("epubSetCover", "Set cover", "epubSetCover(book,bytes,name)"));

        // Add image resource
        registry.register("epubAddImage", (args, vars) -> {
            Book b = (Book) args[0];
            String name = args[1].toString();
            byte[] img = (byte[]) args[2];
            try {
                Resource r = new Resource(new ByteArrayInputStream(img), name);
                r.setMediaType(MediatypeService.JPG);
                b.getResources().add(r);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return name;
        }, new FunctionData("epubAddImage", "Add image", "epubAddImage(book,name,bytes)"));

        // Save EPUB to file
        registry.register("epubSave", (args, vars) -> {
            Book b = (Book) args[0];
            String path = args[1].toString();
            try (FileOutputStream out = new FileOutputStream(path)) {
                new EpubWriter().write(b, out);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, new FunctionData("epubSave", "Save epub", "epubSave(book,path)"));
    }
}