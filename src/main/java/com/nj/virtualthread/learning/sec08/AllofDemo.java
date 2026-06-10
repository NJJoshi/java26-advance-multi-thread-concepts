package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.sec08.aggregator.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class AllofDemo {
    private static final Logger log = LoggerFactory.getLogger(AllofDemo.class);

    public static void main(String[] args) {

        // beans / singletons
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        // create futures
        var futures = IntStream.rangeClosed(51, 150)
                .mapToObj(id -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return aggregator.getProductDto(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor))
                .toList();

        // wait for all the completable-futures to complete
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        log.info("list: {}", list);
    }
}
