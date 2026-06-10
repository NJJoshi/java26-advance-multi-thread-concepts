package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsyncDemo {
    private static final Logger  LOG = LoggerFactory.getLogger(RunAsyncDemo.class);

    static void main(String[] args) {
        LOG.info("main starts");

        runAsync()
                .thenRun(() -> LOG.info("it is done"))
                .exceptionally(ex -> {
                    LOG.info("error - {}", ex.getMessage());
                    return null;
                });

        LOG.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync() {
        LOG.info("method starts");

        var cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
//             LOG.info("task completed");
            throw new RuntimeException("oops");
        }, Executors.newSingleThreadExecutor());

        LOG.info("method ends");
        return cf;
    }
}
