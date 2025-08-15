# Aliva Language

**Aliva** is a lightweight domain‑specific scripting language for building reliable, maintainable web scraping workflows.  
It offers a clean, readable syntax and a standard library tailored specifically for extracting and processing structured data from the web.

The language focuses on the core scraping pipeline:
1. **Fetching** content
2. **Parsing** HTML
3. **Selecting** elements or attributes
4. **Transforming** and **saving** results

Advanced features such as EPUB generation or AI‑assisted processing are intentionally excluded from the core and will be delivered as optional extensions.

---

## Key Features

- **Scraping‑focused standard library**  
  Fetch pages, parse HTML, and query DOM elements with expressive CSS selectors.

- **Built‑in text manipulation**  
  Replace patterns, extract substrings, join lists, handle missing values, and more.

- **Straightforward control flow**  
  Variables, loops, and conditionals for building complex scraping logic without ceremony.

- **File system integration**  
  Create directories, read/write files, and persist progress for resumable scraping.

- **Extensible by design**  
  New capabilities can be added as modules without altering the core interpreter.

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
 │   │   ├── antlr4/             # ANTLR grammar definition (.g4)
 │   │   ├── java/
 │   │   │   └── io/github/synte/aliva/
 │   │   │       ├── parser/     # Generated parser & lexer
 │   │   │       ├── runtime/    # Interpreter & core modules
 │   │   │       └── Main.java
 │   └── test/                   # Unit tests
 ├── scripts/                    # Example .aliva scripts
 ├── pom.xml
 └── README.md
```

---

## Getting Started

### Prerequisites
- **Java** 21 or later  
- **Maven** 3.9+  

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

Aliva is designed to be modular.  
Extensions can provide new built‑in functions or integrate with third‑party services and APIs.

Potential extensions include:
- EPUB creation for scraped content
- AI‑powered text classification or summarization
- Data export to formats like CSV, JSON, or databases

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.