package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.sec07.aggregator.AggregatorService;
import com.nj.virtualthread.learning.sec07.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger LOG = LoggerFactory.getLogger(AggregatorDemo.class);

    static void main() {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);
        var futures = IntStream.rangeClosed(1, 50).
                mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id)))
                .toList();
        var list = futures.stream().map(AggregatorDemo::toProductDto).toList();
        LOG.info("List: {}", list);
    }

    private static ProductDto toProductDto(Future<ProductDto> future){
        try {
            return future.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
