package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.sec07.concurrencylimit.ConcurrencyLimiter;
import com.nj.virtualthread.learning.sec07.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class ConcurrencyLimitWithSemaphoreDemo {
    private static Logger  logger = LoggerFactory.getLogger(ConcurrencyLimitWithSemaphoreDemo.class);

    static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("NJ-VT-",1).factory();
        var limiter = new ConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory), 3);
        execute(limiter, 50);
    }

    private static void execute(ConcurrencyLimiter limiter, int taskCount) throws Exception {
        try(limiter) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                limiter.submit(() -> printProductInfo(j));
            }
            logger.info("All tasks submitted");
        }
    }

    // 3rd party service
    // contract: 3 concurrent calls are allowed
    private static String printProductInfo(int id){
        var product = Client.getProduct(id);
        logger.info("{} => {}", id, product);
        return product;
    }

}
