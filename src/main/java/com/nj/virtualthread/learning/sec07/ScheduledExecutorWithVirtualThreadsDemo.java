package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.sec07.externalservice.Client;
import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorWithVirtualThreadsDemo {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ScheduledExecutorWithVirtualThreadsDemo.class);

    static void main(String[] args) {
        scheduled();
    }

    // To schedule tasks periodically
    private static void scheduled(){
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduler; executor){
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> printProductInfo(1));
            }, 0, 3, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id){
        LOG.info("{} => {}", id, Client.getProduct(id));
    }
}
