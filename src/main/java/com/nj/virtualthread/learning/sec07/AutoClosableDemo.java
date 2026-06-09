package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

public class AutoClosableDemo {
    private static final Logger LOG = LoggerFactory.getLogger(AutoClosableDemo.class);

    static void main(String[] args) {
//        var executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(AutoClosableDemo::task);
//        LOG.info("Submitted task");
        try(var executorService = Executors.newSingleThreadExecutor()){
            executorService.execute(AutoClosableDemo::task);
            executorService.execute(AutoClosableDemo::task);
            executorService.execute(AutoClosableDemo::task);
            executorService.execute(AutoClosableDemo::task);
            executorService.execute(AutoClosableDemo::task);
            LOG.info("Submitted task");
        }
    }

    private static void task() {
        CommonUtils.sleep(Duration.ofSeconds(2));
        LOG.info("Task executed");
    }
}
