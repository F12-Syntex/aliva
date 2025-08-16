# Arguments Feature for Aliva Language

## Overview

This document explains how to add script arguments support to the Aliva language. The changes allow scripts to access command-line arguments through a special `arguments` variable.

## Changes Made

### 1. Grammar Modification

The grammar file `ScraperDSL.g4` was updated to support an optional arguments declaration at the beginning of a script:

```antlr
script
    : (ARGUMENTS '(' (ID (',' ID)*)? ')')? statement* EOF
    ;
```

This would allow scripts to declare their expected arguments like:

```aliva
arguments(arg1, arg2, arg3)
```

### 2. DSLInterpreter Enhancement

The `DSLInterpreter` class was modified to accept and store script arguments:

```java
public class DSLInterpreter extends ScraperDSLBaseVisitor<Object> {
    private final Map<String, Object> variables = new HashMap<>();
    private final FunctionRegistry functions = new FunctionRegistry();
    private String[] scriptArgs = new String[0];

    public void setScriptArgs(String[] args) {
        this.scriptArgs = args;
        // Add the arguments to the interpreter's variables
        List<String> argsList = new ArrayList<>();
        for (String arg : args) {
            argsList.add(arg);
        }
        variables.put("arguments", argsList);
    }
}
```

### 3. Main Entry Point Update

The `Main.java` file was updated to pass command-line arguments to the interpreter:

```java
public static void main(String[] args) throws Exception {
    if (args.length == 0) {
        System.err.println("Usage: java -jar aliva.jar <script-file> [script-arguments...]");
        System.exit(1);
    }

    // First argument is the script file, the rest are script arguments
    String scriptFile = args[0];
    String[] scriptArgs = Arrays.copyOfRange(args, 1, args.length);

    // ... parsing code ...

    DSLInterpreter interpreter = new DSLInterpreter();
    interpreter.setScriptArgs(scriptArgs);
    interpreter.visit(tree);
}
```

### 4. PredefinedRunner Modification

The `PredefinedRunner.java` was updated to call a specific script with hardcoded arguments:

```java
public class PredefinedRunner {
    public static void main(String[] args) throws Exception {
        // Hardcoded path to a script file
        String scriptPath = "scripts/manga_downloader.aliva";
        
        // Hardcoded arguments for the script
        String[] scriptArgs = {"arg1", "arg2", "arg3"};
        
        System.out.println("=== Running: " + scriptPath + " with arguments: " + String.join(", ", scriptArgs) + " ===");
        runScript(scriptPath, scriptArgs);
        System.out.println();
    }

    private static void runScript(String scriptFile, String[] scriptArgs) throws Exception {
        // ... parsing code ...
        
        DSLInterpreter interpreter = new DSLInterpreter();
        interpreter.setScriptArgs(scriptArgs);
        interpreter.visit(tree);
    }
}
```

## Usage Example

A test script `scripts/test_args.aliva` was created to demonstrate usage:

```aliva
// Test script to demonstrate argument passing
println("Number of arguments: " + length(arguments))

i = 0
while (i < length(arguments)) {
    println("Argument " + i + ": " + get(arguments, i))
    i = i + 1
}

// Example usage of arguments
if (length(arguments) >= 1) {
    firstArg = get(arguments, 0)
    println("First argument is: " + firstArg)
}
```

## How to Build and Test

To use this feature, you would need to:

1. Regenerate the ANTLR parser files with the updated grammar
2. Compile the Java code
3. Run a script with arguments:

```bash
java -jar aliva.jar scripts/test_args.aliva first second third
```

This would output:
```
Number of arguments: 3
Argument 0: first
Argument 1: second
Argument 2: third
First argument is: first
```

## Integration with Existing Scripts

The feature was designed to be backward compatible. Scripts that don't use arguments will continue to work as before. The `arguments` variable is only populated when script arguments are provided.

## Future Improvements

Possible enhancements could include:
1. Named argument support in the grammar
2. Type checking for arguments
3. Default argument values
4. Better error handling for missing required arguments
