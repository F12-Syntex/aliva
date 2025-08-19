# 📖 Aliva DSL

**Aliva DSL** is a lightweight **domain‑specific scripting language** for **web scraping**, designed with a focus on extracting **media content** (novels, manga, images) and packaging it (e.g. into EPUB).

It abstracts away the complexity of HTTP requests, HTML parsing, browser automation, file I/O, and EPUB generation into a **clean, minimal scripting language**.

---

## ✨ Why Aliva?

-   **Simple syntax** — inspired by mainstream programming languages.
-   **Media‑first scraping** — built‑in tools for chapters, images, EPUBs.
-   **Extensible runtime** — add your own function libraries.
-   **Focus on *what* to scrape, not *how*.**

---

## 🚀 Quick Start

### Requirements

-   Java **17+**
-   [Maven](https://maven.apache.org/) (or Gradle) to build
-   (Optional) [Playwright for Java](https://playwright.dev/java/) for dynamic pages

### Build

```bash
mvn package
```

This produces `target/aliva.jar`.

### Run a Script

```bash
java -jar target/aliva.jar path/to/script.aliva [arguments...]
```

Example:

```bash
java -jar target/aliva.jar scripts/test.aliva "https://example.com/manga"
```

### Predefined Runner (demo)

For quick experimentation, you can run the hard‑coded demo runner:

```bash
mvn exec:java -Dexec.mainClass="io.github.synte.aliva.PredefinedRunner"
```

---

## 📝 Language

Aliva DSL is a **minimal but expressive scripting language**:

-   Variables & types (`string`, `number`, `boolean`, `list`, `map`)
-   Expressions, conditionals, loops, functions
-   Built‑in scraping & file functions

📚 **See the full [Language Reference](./LANGUAGE_REFERENCE.md)** for syntax and all built‑in functions.

---

## 📚 Example Script

Scrape a novel and build an EPUB:

```dsl
arguments(url)doc page = safeFetch(url)list chapters = selectAllAttr(page, "a.chapter-link", "href")epubCreate("novel")epubMetadata(novel, "My Novel", "Unknown", "en")for (chUrl in chapters) {    doc chPage = safeFetch(chUrl)    string title = selectText(chPage, "h1")    string content = selectHtml(chPage, "div.content")    epubAddChapter(novel, title, content)}epubSave(novel, "output.epub")
```

---

## 🧪 Testing

Unit tests are written using **JUnit 5**:

```bash
mvn test
```

There are also integration tests for browser automation (tagged with `@Tag("browser")`).

---

## 🛠 Roadmap

-   More robust error handling
-   Built‑in scheduling for scraping jobs
-   Headless browser screenshot support
-   Community scraping templates

---

## 📜 License

MIT License © 2025

---

👉 With Aliva DSL you focus on **what to scrape**, not **how to scrape**.```