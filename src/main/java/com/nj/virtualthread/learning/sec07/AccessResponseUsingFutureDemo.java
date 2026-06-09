package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AccessResponseUsingFutureDemo {
    private static final Logger LOG = LoggerFactory.getLogger(AccessResponseUsingFutureDemo.class);

    static void main() {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var product1 = executor.submit(() -> Client.getProduct(1));
            var product2 = executor.submit(() -> Client.getProduct(2));
            var product3 = executor.submit(() -> Client.getProduct(3));

            LOG.info("Product-1: {}", product1.get());
            LOG.info("Product-2: {}", product2.get());
            LOG.info("Product-3: {}", product3.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
