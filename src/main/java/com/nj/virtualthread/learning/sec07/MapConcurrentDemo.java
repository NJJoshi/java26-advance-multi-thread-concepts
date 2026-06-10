package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class MapConcurrentDemo {
    private static final Logger  LOG = LoggerFactory.getLogger(MapConcurrentDemo.class);

    static void main() {
        var list = IntStream.rangeClosed(1, 50)
                .boxed()
                .gather(Gatherers.mapConcurrent(50 , MapConcurrentDemo::getProductName))
                .toList();
        LOG.info("Size: {}", list.size());
    }

    private static String getProductName(int id){
        var product = Client.getProduct(id);
        LOG.info("{} => {}", id, product);
        return product;
    }
}
