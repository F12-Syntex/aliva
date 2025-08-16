package io.github.synte.aliva.runtime;

import java.util.Map;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadFunctions {
    private static final ExecutorService globalThreadPool = Executors.newFixedThreadPool(
        Math.max(2, Runtime.getRuntime().availableProcessors())
    );
    
    private static final Map<String, Object> threadSafeStorage = new ConcurrentHashMap<>();
    
    public static void register(FunctionRegistry registry) {
        registry.register("threadPoolShutdown", (args, vars) -> {
            globalThreadPool.shutdown();
            try {
                if (!globalThreadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    globalThreadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                globalThreadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
            return null;
        });

        registry.register("threadSubmit", (args, vars) -> {
            Runnable task = () -> {
                if (args[0] instanceof Runnable r) {
                    r.run();
                }
            };
            return globalThreadPool.submit(task);
        });

        registry.register("threadWaitForAll", (args, vars) -> {
            if (!(args[0] instanceof List)) {
                throw new RuntimeException("threadWaitForAll expects a list of futures");
            }
            List<?> futures = (List<?>) args[0];
            for (Object f : futures) {
                if (f instanceof Future<?> future) {
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("Thread execution failed: " + e.getMessage());
                    }
                }
            }
            return null;
        });

        registry.register("threadSafeSet", (args, vars) -> {
            String key = String.valueOf(args[0]);
            Object value = args[1];
            threadSafeStorage.put(key, value);
            return null;
        });

        registry.register("threadSafeGet", (args, vars) -> {
            String key = String.valueOf(args[0]);
            return threadSafeStorage.get(key);
        });

        registry.register("threadSafeClear", (args, vars) -> {
            threadSafeStorage.clear();
            return null;
        });

        registry.register("threadBatchSubmit", (args, vars) -> {
            if (!(args[0] instanceof List)) {
                throw new RuntimeException("threadBatchSubmit expects a list of tasks");
            }
            List<?> tasks = (List<?>) args[0];
            List<Future<?>> futures = new ArrayList<>();
            
            for (Object task : tasks) {
                if (task instanceof Runnable r) {
                    futures.add(globalThreadPool.submit(r));
                }
            }
            return futures;
        });

        registry.register("threadIsComplete", (args, vars) -> {
            if (args[0] instanceof Future<?> future) {
                return future.isDone();
            }
            throw new RuntimeException("threadIsComplete expects a Future object");
        });

        registry.register("threadGetResult", (args, vars) -> {
            if (args[0] instanceof Future<?> future) {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException("Failed to get thread result: " + e.getMessage());
                }
            }
            throw new RuntimeException("threadGetResult expects a Future object");
        });
    }
}
