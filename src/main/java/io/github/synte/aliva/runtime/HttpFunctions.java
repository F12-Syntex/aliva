package io.github.synte.aliva.runtime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpFunctions {
    private static final OkHttpClient http = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build();

    public static void register(FunctionRegistry registry) {
        registry.register("fetch", (args, vars) -> fetchGet(args[0].toString()));
        registry.register("fetchPost", (args, vars) -> fetchPost(args[0].toString(), (Map<String, String>) args[1]));
        registry.register("fetchLocal", (args, vars) -> Jsoup.parse(args[0].toString()));
        registry.register("safeFetch", (args, vars) -> {
            String url = args[0].toString();
            int maxRetries = args.length > 1 ? ((Number) args[1]).intValue() : 5;
            int delaySeconds = args.length > 2 ? ((Number) args[2]).intValue() : 30;
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    return fetchGet(url);
                } catch (RuntimeException e) {
                    if (e.toString().contains("429") && attempt < maxRetries) {
                        try { Thread.sleep(delaySeconds * 1000L); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                        continue;
                    }
                    throw e;
                }
            }
            throw new RuntimeException("safeFetch failed after " + maxRetries + " retries: " + url);
        });
    }

    private static Document fetchGet(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            try (Response resp = http.newCall(request).execute()) {
                if (!resp.isSuccessful()) throw new IOException("HTTP error: " + resp.code());
                return Jsoup.parse(resp.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document fetchPost(String url, Map<String, String> params) {
        try {
            FormBody.Builder form = new FormBody.Builder();
            params.forEach(form::add);
            Request request = new Request.Builder().url(url).post(form.build()).build();
            try (Response resp = http.newCall(request).execute()) {
                if (!resp.isSuccessful()) throw new IOException("HTTP error: " + resp.code());
                return Jsoup.parse(resp.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}