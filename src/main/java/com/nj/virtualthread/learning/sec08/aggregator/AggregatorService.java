package com.nj.virtualthread.learning.sec08.aggregator;


import com.nj.virtualthread.learning.sec07.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id),  executorService)
                                        .exceptionally(x -> "Unknown")
                                        .orTimeout(750, TimeUnit.SECONDS)
                                        .exceptionally(x -> "Service Timeout");
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executorService)
                                      .exceptionally(x -> -1)
                                        .orTimeout(750, TimeUnit.SECONDS)
                                        .exceptionally(x -> 408);
        return new ProductDto(id, product.get(), rating.get());
    }
}
