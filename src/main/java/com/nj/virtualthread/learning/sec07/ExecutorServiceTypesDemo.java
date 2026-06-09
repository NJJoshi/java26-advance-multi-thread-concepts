package com.nj.virtualthread.learning.sec07;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTypesDemo {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ExecutorServiceTypesDemo.class);

    static void main() {
//        execute(Executors.newSingleThreadExecutor(), 3);
//        execute(Executors.newFixedThreadPool(5), 10);
//        execute(Executors.newCachedThreadPool(), 200);
//        execute(Executors.newVirtualThreadPerTaskExecutor(), 200);
        scheduled();
    }

    private static void scheduled() {
        try(var executorService = Executors.newSingleThreadScheduledExecutor()) {
            executorService.scheduleAtFixedRate(() -> {
                LOG.info("Scheduled task");
            }, 0, 1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(2));
        }
    }

    private static void execute(ExecutorService service, int taskCount) {
        try(service) {
            for(int i = 0; i < taskCount; i++) {
                int j = i;
                service.submit(() -> ioTask(j));
            }
            LOG.info("Submitted task");
        }
    }

    private static void ioTask(int i) {
        LOG.info("Task started: {}, Thread Info: {}, isVirtual: {}", i, Thread.currentThread(), Thread.currentThread().isVirtual());
        CommonUtils.sleep(Duration.ofSeconds(5));
        LOG.info("Task ended: {}, Thread Info: {}, isVirtual: {}", i, Thread.currentThread(), Thread.currentThread().isVirtual());

    }
}
