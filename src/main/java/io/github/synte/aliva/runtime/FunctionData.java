package io.github.synte.aliva.runtime;

public class FunctionData {
    private final String name;
    private final String description;
    private final String usage;

    public FunctionData(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
