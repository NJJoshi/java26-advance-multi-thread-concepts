package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {
    private static final Logger  LOG = LoggerFactory.getLogger(SupplyAsyncDemo.class);

    static void main(String[] args) {
        LOG.info("main start");
        var cf = supplyAsync();
        cf.thenAccept(v -> LOG.info("value = {}", v));
        LOG.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> supplyAsync() {
        LOG.info("supplyAsync start");
        var task = CompletableFuture.supplyAsync(
                () -> {
                    CommonUtils.sleep(Duration.ofSeconds(1));
                    return "Result";
                }, Executors.newVirtualThreadPerTaskExecutor()
        );
        LOG.info("supplyAsync ends");
        return task;
    }
}
