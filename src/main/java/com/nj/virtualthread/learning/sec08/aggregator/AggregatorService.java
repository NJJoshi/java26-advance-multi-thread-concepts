package com.nj.virtualthread.learning.sec08.aggregator;


import com.nj.virtualthread.learning.sec07.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id),  executorService).exceptionally(x -> "Unknown");
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executorService).exceptionally(x -> -1);
        return new ProductDto(id, product.get(), rating.get());
    }
}
