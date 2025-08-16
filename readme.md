# 📖 Aliva DSL — A Web Scraping Scripting Language

**Aliva DSL** is a lightweight domain-specific language (DSL) designed to make **web scraping easy and expressive**, with a strong focus on extracting **media content such as novels and manga**.  

It abstracts away the complexity of HTTP requests, HTML parsing, browser automation, file I/O, and EPUB generation into a simple, clean scripting language.

---

## ✨ Features

- **Simple Syntax** inspired by mainstream programming languages.
- **Built-in scraping functions**:
  - Fetch HTML via HTTP or Playwright browser.
  - Parse HTML with CSS selectors.
- **Media-friendly tools**:
  - Save files, download images, build EPUBs.
- **Data manipulation**:
  - Lists, maps, strings, numbers, booleans.
  - Loops, conditionals, functions.
- **Extensible runtime** with modular function libraries.

---

## 🚀 Getting Started

### Requirements
- Java 17+
- [Maven](https://maven.apache.org/) or Gradle for building
- (Optional) [Playwright for Java](https://playwright.dev/java/) for dynamic page scraping

### Build
```bash
mvn package
```

This produces `aliva.jar`.

### Run a Script
```bash
java -jar target/aliva.jar path/to/script.aliva [arguments...]
```

Example:
```bash
java -jar target/aliva.jar scripts/test.aliva "https://example.com/manga"
```

---

## 📝 Language Syntax

Aliva DSL supports a **minimal but powerful set of constructs**.

### 1. Variables & Types
```dsl
string title = "Hello World"
number count = 42
boolean active = true
list items = ["a", "b", "c"]
map config = {"url": "https://example.com", "retry": 3}
```

Supported types:
- `string`, `number`, `boolean`, `list`, `map`

### 2. Expressions
- Arithmetic: `+ - * / %`
- Comparisons: `< <= > >= == !=`
- Logical: `&& || !`
- Ternary: `condition ? trueValue : falseValue`

```dsl
number x = 5 * 2
print(x > 3 && x < 20 ? "ok" : "fail")
```

### 3. Assignments
```dsl
string name = "Old"
name = "New"

list arr = ["a", "b"]
arr[1] = "c"   // indexed assignment
```

### 4. Control Flow
#### If / Else
```dsl
if (count > 10) {
    print("big")
} else {
    print("small")
}
```

#### While Loop
```dsl
number i = 0
while (i < 5) {
    i = i + 1
}
```

#### For Loop
```dsl
for (item in ["a", "b", "c"]) {
    print(item)
}
```

Supports `break` and `continue`.

### 5. Functions
#### Function Call
```dsl
print("Hello", "World")
```

#### Function Literals
```dsl
fun greet() {
    print("Hello from a function literal")
}
```

---

## 🔧 Built-in Functions

Aliva DSL comes with a **rich library** of pre-defined functions.

### Core
- `print(...)` / `println(...)`
- `get(listOrMap, key)`
- `set(listOrMap, key, value)`
- `append(list, value)`
- `length(listOrString)`

### HTTP & HTML
- `fetch(url)` → `Document`
- `fetchPost(url, mapParams)`
- `safeFetch(url, retries, delaySeconds)`
- `fetchLocal(htmlText)`
- `selectText(doc, selector)`
- `selectAttr(doc, selector, attr)`
- `selectAllText(doc, selector)`
- `selectAll(doc, selector)` → list of elements

### Browser (Playwright)
- `browser b = browserLaunch("playwright", true)`
- `browserGoto(b, url)`
- `browserClick(b, selector)`
- `browserType(b, selector, text)`
- `waitForHydration(b, selector, timeout)`
- `browserContent(b)` → hydrated DOM
- `browserClose(b)`

### Strings
- `replace(text, old, new)`
- `trim(text)`
- `split(text, delimiter)`
- `join(list, delimiter)`
- `concat(a, b, c...)`
- `contains(text, substring)`
- `matches(text, regex)`
- `sanitizeFilename(text)`
- `urlSlug(url)` → last path segment

### Files
- `readFile(path)`
- `writeFile(path, text)`
- `appendFile(path, text)`
- `writeBytes(path, byteArray)`
- `readBytes(path)`
- `fileExists(path)`

### JSON
- `toJson(obj)`
- `parseJson(string)`
- `jsonToMap(string)`

### EPUB
- `epubCreate(varName)`
- `epubMetadata(book, title, author, language)`
- `epubAddChapter(book, title, htmlContent)`
- `epubSetCover(book, imageBytes, "cover.jpg")`
- `epubSave(book, "output.epub")`

### Misc
- `sleep(ms)`
- `random()`
- `formatNumber(number, "0.00")`

---

## 📚 Example: Scrape a Novel into EPUB

```dsl
arguments(url)

doc page = safeFetch(url)
list chapters = selectAllAttr(page, "a.chapter-link", "href")

epubCreate("novel")
epubMetadata(novel, "My Novel", "Unknown", "en")

for (chUrl in chapters) {
    doc chPage = safeFetch(chUrl)
    string title = selectText(chPage, "h1")
    string content = selectHtml(chPage, "div.content")
    epubAddChapter(novel, title, content)
}

epubSave(novel, "output.epub")
```

---

## 🧪 Running Tests

Unit tests are provided using **JUnit 5**.

```bash
mvn test
```

---

## 🛠 Roadmap

- [ ] More robust error handling
- [ ] Built-in scheduling for scraping jobs
- [ ] Headless browser screenshot support
- [ ] Community-driven scraping templates

---

## 📜 License

MIT License © 2025 — Developed by **[University of Kent project team](https://github.com/synte/)**

---

👉 With Aliva DSL, you can focus on **what to scrape**, not **how to scrape**.

---

Would you like me to also create a **syntax reference table (cheat sheet)** that users can quickly glance at, separate from the README?