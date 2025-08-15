package io.github.synte.aliva.runtime;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                        try {
                            Thread.sleep(delaySeconds * 1000L);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                        continue;
                    }
                    throw e;
                }
            }
            throw new RuntimeException("safeFetch failed after " + maxRetries + " retries: " + url);
        });
        registry.register("fetchBytes", (args, vars) -> {
            try (java.io.InputStream in = new java.net.URL(args[0].toString()).openStream()) {
                return in.readAllBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        registry.register("fetchText", (args, vars) -> {
            String url = args[0].toString();
            Map<String, String> headers = (Map<String, String>) args[1];
            HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url));
            headers.forEach(builder::header);
            try {
                HttpResponse<String> resp = HttpClient.newHttpClient()
                        .send(builder.build(), HttpResponse.BodyHandlers.ofString());
                if (resp.statusCode() != 200) {
                    throw new RuntimeException("HTTP error: " + resp.statusCode() + " for " + url);
                }
                return resp.body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Document fetchGet(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            try (Response resp = http.newCall(request).execute()) {
                if (!resp.isSuccessful()) {
                    throw new IOException("HTTP error: " + resp.code());
                }
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
                if (!resp.isSuccessful()) {
                    throw new IOException("HTTP error: " + resp.code());
                }
                return Jsoup.parse(resp.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
