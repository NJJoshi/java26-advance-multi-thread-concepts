package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class GetProductsUsingSupplyAsyncDemo {
    private static final Logger  LOG = LoggerFactory.getLogger(GetProductsUsingSupplyAsyncDemo.class);

    static void main(String[] args) throws ExecutionException, InterruptedException {
        LOG.info("main start");
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var product1 = CompletableFuture.supplyAsync(() -> Client.getProduct(1), executor);
            var product2 = CompletableFuture.supplyAsync(() -> Client.getProduct(2), executor);
            var product3 = CompletableFuture.supplyAsync(() -> Client.getProduct(3), executor);

            LOG.info("Product 1: {}", product1.get());
            LOG.info("Product 2: {}", product2.get());
            LOG.info("Product 3: {}", product3.get());
        }
        LOG.info("main ends");
    }
}
