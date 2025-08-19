package io.github.synte.aliva.runtime;

/**
 * Interface for functions that provide documentation.
 */
public interface DocumentedFunction {
    /**
     * Get the name of the function.
     * @return The function name as it should be called in the DSL
     */
    String getName();

    /**
     * Get a description of what the function does.
     * @return A detailed description of the function's purpose and behavior
     */
    String getDescription();

    /**
     * Get an example of how to use the function.
     * @return A code example showing proper usage of the function
     */
    String getUsage();

    /**
     * Get the return type of the function.
     * @return The type that this function returns
     */
    String getReturnType();

    /**
     * Get a description of the function's parameters.
     * @return A description of each parameter and its purpose
     */
    String getParameterDescription();
}
