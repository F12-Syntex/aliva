# Aliva Language Reference

_Generated Tue, 19 Aug 2025 10:21:37 +0100_

## Language Specification

<details>
<summary>ScraperDSL.g4 (click to expand)</summary>

```antlr
grammar ScraperDSL;

script
    : (ARGUMENTS '(' (ID (',' ID)*)? ')')? statement* EOF
    ;

statement
    : varDecl
    | assignment
    | ifStatement
    | whileStatement
    | forStatement
    | 'break' ';'?
    | 'continue' ';'?
    | funcCall ';'?
    ;

varDecl
    : (STRING_TYPE | NUMBER_TYPE | BOOLEAN_TYPE | LIST_TYPE | MAP_TYPE) ID ('=' expression)? ';'?
    ;

assignment
    : ID '=' expression ';'?
    | ID '[' expression ']' '=' expression ';'?
    ;

ifStatement
    : 'if' '(' expression ')' block ('else' block)?
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

forStatement
    : 'for' '(' ID 'in' expression ')' block
    ;

block
    : '{' statement* '}'
    ;

// Expression with ternary support
expression
    : logicalOrExpr ('?' expression ':' expression)?
    ;

logicalOrExpr
    : logicalAndExpr ('||' logicalAndExpr)*
    ;

logicalAndExpr
    : equalityExpr ('&&' equalityExpr)*
    ;

equalityExpr
    : comparisonExpr (op=('==' | '!=') comparisonExpr)*
    ;

comparisonExpr
    : additiveExpr (op=('<' | '<=' | '>' | '>=') additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr (op=('+' | '-') multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr (op=('*' | '/' | '%') unaryExpr)*
    ;

unaryExpr
    : op=('!' | '-') unaryExpr
    | primary
    ;

// Primary now supports post-indexing on any primary result
primary
    : literal
    | listLiteral
    | mapLiteral
    | variableRef
    | funcCall
    | functionLiteral
    | '(' expression ')'
    | primary '[' expression ']'
    ;

functionLiteral
    : 'fun' '(' ')' block
    ;

literal
    : STRING
    | NUMBER
    | BOOLEAN
    | NULL
    ;

listLiteral
    : '[' (expression (',' expression)*)? ']'
    ;

mapLiteral
    : '{' (mapEntry (',' mapEntry)*)? '}'
    ;

mapEntry
    : STRING ':' expression
    ;

variableRef
    : ID
    | ID '[' expression ']'
    ;

funcCall
    : ID '(' (expression (',' expression)*)? ')'
    ;

// Types
STRING_TYPE : 'string';
NUMBER_TYPE : 'number';
BOOLEAN_TYPE : 'boolean';
LIST_TYPE   : 'list';
MAP_TYPE    : 'map';

// Literals
BOOLEAN : 'true' | 'false';
NULL    : 'null';
NUMBER  : '-'? [0-9]+ ('.' [0-9]+)?;
STRING  : '"' (~["\\] | '\\' .)* '"' | '\'' (~['\\] | '\\' .)* '\'';

// Identifiers
ID : [a-zA-Z_][a-zA-Z0-9_]*;
ARGUMENTS : 'arguments';

// Whitespace & Comments
WS : [ \t\r\n]+ -> skip;
COMMENT : '//' ~[\r\n]* -> skip;
```
</details>

## Functions

- `append(list, value:any)` : Appends a value to a list. List must be mutable.
- `appendFile(path:string, content:string)` : Appends text content (UTF-8) to a file, creating it if needed.
- `browserClick(browser:BrowserEngine, selector:string)` : Clicks an element matching the selector.
- `browserClose(browser:BrowserEngine)` : Closes the browser and releases resources.
- `browserContent(browser:BrowserEngine) -> Document` : Returns the current page content parsed as HTML Document.
- `browserCurrentUrl(browser:BrowserEngine) -> string` : Returns the current page URL.
- `browserGoto(browser:BrowserEngine, url:string)` : Navigates the browser to a specified URL.
- `browserLaunch([headless:boolean]) -> BrowserEngine` : Launches a new browser instance with optional headless mode and returns the browser reference.
- `browserScroll(browser:PlaywrightEngine, pixels:number)` : Scrolls the page vertically by a number of pixels.
- `browserType(browser:BrowserEngine, selector:string, text:string)` : Types text into an element matching the selector.
- `browserWaitForSelector(browser:BrowserEngine, selector:string, timeoutMs:number)` : Waits for an element matching the selector within the timeout.
- `classOf(value:any) -> string` : Returns the runtime class name of a value.
- `concat(...strings) -> string` : Concatenates multiple strings into one.
- `contains(string:string, sequence:string) -> boolean` : Checks if the string contains the specified sequence of char values.
- `dump(value:any)` : Pretty-prints nested lists and maps to stdout.
- `epubAddChapter(book:Book, title:string, htmlContent:string, [fileName:string])` : Adds an HTML chapter to the EPUB.
- `epubAddImage(book:Book, imageName:string, imageBytes:byte[]) -> string` : Adds an image resource to the EPUB and returns the resource name.
- `epubAddTextChapter(book:Book, title:string, text:string, [fileName:string])` : Wraps plain text into minimal HTML and adds it as a chapter.
- `epubCreate(varName:string) -> Book` : Creates a new EPUB Book and stores it into variables by a given name.
- `epubMetadata(book:Book, title:string, author:string, language:string)` : Sets title, author and language metadata on the EPUB.
- `epubSave(book:Book, filePath:string)` : Saves the EPUB to a file path.
- `epubSetCover(book:Book, imageBytes:byte[], [imageName:string])` : Sets the cover image for the EPUB.
- `fileExists(path:string) -> boolean` : Checks whether a file exists.
- `flatten(list<list|any>) -> list` : Flattens a one-level nested list.
- `formatNumber(number:number, pattern:string) -> string` : Formats a number using a DecimalFormat pattern.
- `get(target:list|map, key:number|string) -> any` : Gets an element from a list by index or a map by key.
- `getNumbers(string) -> list<string>` : Extracts all sequences of digits from the string.
- `html(doc:Document) -> string` : Returns the full HTML string of the given Document.
- `indexOf(list, value:any) -> number` : Returns the index of the first occurrence of value in a list, or -1.
- `isList(value:any) -> boolean` : Checks if a value is a list.
- `isMap(value:any) -> boolean` : Checks if a value is a map.
- `join(list:list<string>, delimiter:string) -> string` : Joins a list of strings with the specified delimiter.
- `jsonToMap(json:string) -> map` : Parses a JSON object string into a Map.
- `length(value:list|string|array) -> number` : Returns the length of a list, string, or array (including byte[]).
- `lower(string) -> string` : Converts all of the characters in the string to lowercase.
- `matches(string:string, regex:string) -> boolean` : Determines if the string matches the given regex.
- `mkdirs(path:string)` : Creates directories recursively.
- `parseJson(json:string) -> any` : Parses a JSON string into Java objects (Map/List/primitive).
- `print(...values) -> string` : Prints arguments without a trailing newline. Also returns the printed string.
- `println(...values) -> string` : Prints arguments followed by a newline. Also returns the printed string.
- `random() -> number` : Returns a random number in [0, 1).
- `range(start:number, end:number, [step:number]) -> list<number>` : Creates a list of numbers from start to end inclusive, with optional step.
- `readBytes(path:string) -> byte[]` : Reads a file as binary data, or empty byte[] if it does not exist.
- `readFile(path:string) -> string` : Reads a text file (UTF-8) and returns its contents, or empty string if it does not exist.
- `repeat(value:any, times:number) -> list` : Creates a list by repeating a value N times.
- `replace(original:string, target:string, replacement:string) -> string` : Replaces occurrences of a substring within a string with another string.
- `replaceAll(string:string, regex:string, replacement:string) -> string` : Replaces each substring of this string that matches the given regex with the replacement.
- `reverse(list) -> list` : Returns a reversed copy of a list.
- `sanitizeFilename(string) -> string` : Sanitizes a string for use as a filename by replacing disallowed characters with underscores.
- `selectAll(doc:Document, selector:string) -> list<Element>` : Selects all elements by CSS selector and returns them as a list of elements.
- `selectAllAttr(doc:Document, selector:string, attr:string) -> list<string>` : Selects all elements by CSS selector and returns the specified attribute values (empty when missing).
- `selectAllText(doc:Document, selector:string) -> list<string>` : Selects all elements by CSS selector and returns their text content as a list.
- `selectAttr(doc:Document, selector:string, attr:string) -> string` : Selects the first element by CSS selector and returns an attribute value, or empty string.
- `selectHtml(doc:Document, selector:string) -> string` : Selects the first element by CSS selector and returns its outer HTML, or empty string.
- `selectText(doc:Document, selector:string) -> string` : Selects the first element by CSS selector and returns its text, or empty string.
- `set(target:list|map, key:number|string, value:any)` : Sets a value on a list by index or a map by key. List must be mutable.
- `sleep(ms:number)` : Sleeps for the given number of milliseconds.
- `slice(list, start:number, end:number) -> list` : Returns a sublist from start index (inclusive) to end index (exclusive, clamped).
- `sortBy(listOfMaps:list<map>, key:string) -> list<map>` : Returns a new list of maps sorted ascending by the specified key.
- `split(string:string, regex:string) -> list<string>` : Splits the string around matches of the given regex.
- `toJson(value:any) -> string` : Serializes a value to a compact JSON string.
- `toNumber(value:any) -> number` : Converts a value to a number or throws if it cannot be parsed.
- `toString(value:any) -> string` : Converts a value to a string, null becomes empty string.
- `trim(string) -> string` : Trims whitespace from the start and end of the string.
- `unique(list) -> list` : Returns a list with duplicate elements removed (stable order).
- `urlSlug(url:string) -> string` : Extracts the last path segment from a URL (without query).
- `waitForHydration(browser:BrowserEngine, selector:string, [timeoutMs:number])` : Waits for a selector indicating client-side hydration to complete.
- `writeBytes(path:string, data:byte[])` : Writes binary data to a file.
- `writeFile(path:string, content:string)` : Writes text content (UTF-8) to a file, creating parent directories if necessary.
