```markdown
# Aliva Language

**Aliva** is a domain‑specific scripting language for building robust and maintainable web scraping workflows.  
It combines a simple, readable syntax with a focused standard library of scraping and text‑processing functions.

The core language is intentionally minimal — it provides only the tools necessary to fetch, parse, and process web content.  
Additional functionality, such as EPUB generation or AI‑assisted processing, will be delivered as optional extensions.

---

## Features

- **Scraping‑oriented standard library**  
  Built‑in functions for HTTP fetching, HTML parsing, CSS selection, and attribute/text extraction.
- **Text processing utilities**  
  Includes pattern replacement, substring extraction, joining/splitting, and blank‑value handling.
- **Control flow**  
  Variables, loops, and conditionals for building dynamic scraping logic.
- **Filesystem support**  
  Create directories, read and write files, and track progress across sessions.
- **Extensible architecture**  
  Designed for integration with external modules without modifying the core interpreter.

---

## Example Script

```aliva
string baseUrl = "https://example.com/articles"

doc page = fetch(baseUrl)
list titles = selectAllText(page, "h2.article-title")

print("Found", length(titles), "articles")

number i = 1
for title in titles {
    print(i, ":", title)
    i = i + 1
}
```

---

## Project Structure

```
aliva/
 ├── src/
 │   ├── main/
 │   │   ├── antlr4/         # ANTLR grammar (.g4)
 │   │   ├── java/
 │   │   │   └── io/github/synte/aliva/
 │   │   │       ├── parser/ # Generated parser & lexer
 │   │   │       ├── runtime/# Interpreter & core modules
 │   │   │       └── Main.java
 │   └── test/               # Unit tests
 ├── scripts/                # Example .aliva scripts
 ├── pom.xml
 └── README.md
```

---

## Getting Started

### Requirements
- Java 21 or later
- Maven 3.9 or later

### Build
```bash
mvn clean install
```

### Run a Script
```bash
java -cp target/aliva-1.0-SNAPSHOT.jar io.github.synte.aliva.Main scripts/test.aliva
```

---

## Extending Aliva

Aliva supports external modules for extending functionality.  
Modules can provide new functions, integrate third‑party libraries, or connect to external services.

Example extensions:
- EPUB generation
- AI‑assisted content classification
- API integrations

---

## License

Distributed under the MIT License. See `LICENSE` for details.
```

---

If you want, I can now draft a **cleaned‑up `test.aliva` example** that mirrors your previous `test.dsl` but removes the EPUB logic so it works as a core‑language‑only demo.  

Do you want me to prepare that next?