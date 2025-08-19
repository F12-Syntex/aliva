package io.github.synte.aliva.runtime.functions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.synte.aliva.runtime.FunctionData;
import io.github.synte.aliva.runtime.FunctionRegistry;

public class StringFunctions {

    public static void register(FunctionRegistry registry) {
        registry.register("replace", (args, vars) -> String.valueOf(args[0])
                .replace(String.valueOf(args[1]), String.valueOf(args[2])),
                new FunctionData("replace",
                        "Replaces occurrences of a substring within a string with another string.",
                        "replace(original:string, target:string, replacement:string) -> string"));

        registry.register("trim", (args, vars) -> String.valueOf(args[0]).trim(),
                new FunctionData("trim",
                        "Trims whitespace from the start and end of the string.",
                        "trim(string) -> string"));

        registry.register("base64Decode", (args, vars) -> {
            String input = String.valueOf(args[0]);
            return new String(java.util.Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
        }, new FunctionData("base64Decode",
                "Decodes a Base64 encoded string.",
                "base64Decode(encoded:string) -> string"));

        registry.register("split", (args, vars) -> Arrays.asList(String.valueOf(args[0])
                .split(String.valueOf(args[1]))),
                new FunctionData("split",
                        "Splits the string around matches of the given regex.",
                        "split(string:string, regex:string) -> list<string>"));

        registry.register("join", (args, vars) -> String.join(String.valueOf(args[1]),
                castToStringList(args[0])),
                new FunctionData("join",
                        "Joins a list of strings with the specified delimiter.",
                        "join(list:list<string>, delimiter:string) -> string"));

        registry.register("startsWith", (args, vars) -> String.valueOf(args[0])
                .startsWith(String.valueOf(args[1])),
                new FunctionData("startsWith",
                        "Checks if the string starts with the specified prefix.",
                        "startsWith(string:string, prefix:string) -> boolean"));

        registry.register("concat", (args, vars) -> {
            StringBuilder sb = new StringBuilder();
            for (Object a : args) {
                sb.append(normalizeToString(a));
            }
            return sb.toString();
        }, new FunctionData("concat",
                "Concatenates multiple strings into one.",
                "concat(...strings) -> string"));

        registry.register("lower", (args, vars) -> String.valueOf(args[0]).toLowerCase(),
                new FunctionData("lower",
                        "Converts all of the characters in the string to lowercase.",
                        "lower(string) -> string"));

        registry.register("contains", (args, vars) -> String.valueOf(args[0])
                .contains(String.valueOf(args[1])),
                new FunctionData("contains",
                        "Checks if the string contains the specified sequence of char values.",
                        "contains(string:string, sequence:string) -> boolean"));

        registry.register("matches", (args, vars) -> String.valueOf(args[0])
                .matches(String.valueOf(args[1])),
                new FunctionData("matches",
                        "Determines if the string matches the given regex.",
                        "matches(string:string, regex:string) -> boolean"));

        registry.register("sanitizeFilename", (args, vars) -> String.valueOf(args[0])
                .replaceAll("[^a-zA-Z0-9-_]", "_").trim(),
                new FunctionData("sanitizeFilename",
                        "Sanitizes a string for use as a filename by replacing disallowed characters with underscores.",
                        "sanitizeFilename(string) -> string"));

        registry.register("replaceAll", (args, vars) -> {
            if (args.length < 3) {
                throw new RuntimeException("replaceAll(text, regex, replacement) requires 3 arguments");
            }
            String text = args[0] != null ? args[0].toString() : "";
            String regex = args[1] != null ? args[1].toString() : "";
            String replacement = args[2] != null ? args[2].toString() : "";
            return text.replaceAll(regex, replacement);
        }, new FunctionData("replaceAll",
                "Replaces each substring of this string that matches the given regex with the replacement.",
                "replaceAll(string:string, regex:string, replacement:string) -> string"));

        registry.register("urlSlug", (args, vars) -> {
            String url = String.valueOf(args[0]);
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            String[] parts = url.split("/");
            String last = parts[parts.length - 1];
            int qIdx = last.indexOf('?');
            if (qIdx >= 0) {
                last = last.substring(0, qIdx);
            }
            return last.trim();
        }, new FunctionData("urlSlug",
                "Extracts the last path segment from a URL (without query).",
                "urlSlug(url:string) -> string"));

        registry.register("getNumbers", (args, vars) -> {
            String input = String.valueOf(args[0]);
            List<String> numbers = new ArrayList<>();
            Matcher m = Pattern.compile("\\d+").matcher(input);
            while (m.find()) {
                numbers.add(m.group());
            }
            return numbers;
        }, new FunctionData("getNumbers",
                "Extracts all sequences of digits from the string.",
                "getNumbers(string) -> list<string>"));

        // Add this to your StringFunctions.register() method:
        registry.register("cleanUrl", (args, vars) -> {
            String url = String.valueOf(args[0]);
            if (url == null || url.isEmpty()) {
                return "";
            }

            // Remove all types of whitespace from start and end
            url = url.trim();

            // Remove common invisible characters that might cause issues
            url = url.replaceAll("[\u00A0\u2000-\u200F\u2028-\u202F\u205F-\u206F\uFEFF]", "");

            // Remove any remaining leading/trailing whitespace after invisible char removal
            url = url.trim();

            // Basic URL validation - must start with http:// or https://
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return "";
            }

            return url;
        }, new FunctionData("cleanUrl",
                "Cleans a URL by removing whitespace and invisible characters, returns empty string if invalid.",
                "cleanUrl(url:string) -> string"));

        registry.register("isValidUrl", (args, vars) -> {
            String url = String.valueOf(args[0]);
            if (url == null || url.isEmpty()) {
                return false;
            }

            // Basic URL validation
            url = url.trim();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return false;
            }

            // Check for obvious invalid characters
            if (url.contains(" ") || url.contains("\n") || url.contains("\t")) {
                return false;
            }

            return true;
        }, new FunctionData("isValidUrl",
                "Checks if a string is a valid HTTP/HTTPS URL.",
                "isValidUrl(url:string) -> boolean"));

        registry.register("debugString", (args, vars) -> {
            String input = String.valueOf(args[0]);
            StringBuilder sb = new StringBuilder();
            sb.append("String: '").append(input).append("'\n");
            sb.append("Length: ").append(input.length()).append("\n");
            sb.append("Chars: ");
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c < 32 || c > 126) {
                    sb.append("[").append((int) c).append("]");
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }, new FunctionData("debugString",
                "Returns debug information about a string including invisible characters.",
                "debugString(string:string) -> string"));

        registry.register("removeLeadingChar", (args, vars) -> {
            String input = String.valueOf(args[0]);
            String charToRemove = String.valueOf(args[1]);

            while (input.startsWith(charToRemove)) {
                input = input.substring(charToRemove.length());
            }
            return input;
        }, new FunctionData("removeLeadingChar",
                "Removes all occurrences of a character from the beginning of a string.",
                "removeLeadingChar(string:string, char:string) -> string"));

        registry.register("forceCleanUrl", (args, vars) -> {
            String url = String.valueOf(args[0]);
            if (url == null || url.isEmpty()) {
                return "";
            }

            // Very aggressive cleaning
            StringBuilder cleaned = new StringBuilder();
            for (int i = 0; i < url.length(); i++) {
                char c = url.charAt(i);
                // Only keep printable ASCII characters and some common URL characters
                if ((c >= 32 && c <= 126) || c == '%') {
                    cleaned.append(c);
                }
            }

            String result = cleaned.toString().trim();

            // Remove leading spaces that might still be there
            while (result.startsWith(" ")) {
                result = result.substring(1);
            }

            // Ensure it's a valid URL
            if (!result.startsWith("http://") && !result.startsWith("https://")) {
                return "";
            }

            return result;
        }, new FunctionData("forceCleanUrl",
                "Aggressively cleans a URL by removing all non-printable characters.",
                "forceCleanUrl(url:string) -> string"));
    }

    private static List<String> castToStringList(Object obj) {
        if (obj instanceof List<?> rawList) {
            List<String> out = new ArrayList<>(rawList.size());
            for (Object o : rawList) {
                out.add(normalizeToString(o));
            }
            return out;
        }
        throw new RuntimeException("Expected a list for join()");
    }

    private static String normalizeToString(Object val) {
        if (val == null) {
            return "null";
        }
        if (val instanceof Number n) {
            if (n instanceof Double d && d % 1 == 0) {
                return Long.toString(d.longValue());
            }
            if (n instanceof Float f && f % 1 == 0) {
                return Long.toString(f.longValue());
            }
            // Other numbers: use toString
            return n.toString();
        }
        return val.toString();
    }
}
