package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.sec08.aggregator.AggregatorService;
import org.slf4j.Logger;

import java.util.concurrent.Executors;

public class AggregatorUsingSupplyAsyncDemo {
    private static final Logger  LOG = org.slf4j.LoggerFactory.getLogger(AggregatorUsingSupplyAsyncDemo.class);

    static void main(String[] args) throws Exception {
        var executorService = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executorService);
        LOG.info("Product = {}", aggregator.getProductDto(61));
    }
}
