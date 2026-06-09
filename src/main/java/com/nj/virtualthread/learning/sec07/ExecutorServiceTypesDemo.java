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
//        single();
//        fixed();
//        cached();
//        virtual();
        scheduled();
    }

    // single thread executor - to execute tasks sequentially
    private static void single(){
        execute(Executors.newSingleThreadExecutor(), 3);
    }

    // fixed thread pool
    private static void fixed(){
        execute(Executors.newFixedThreadPool(5), 20);
    }

    // elastic thread pool
    private static void cached(){
        execute(Executors.newCachedThreadPool(), 200);
    }

    // ExecutorService which creates VirtualThread per task
    private static void virtual(){
        execute(Executors.newVirtualThreadPerTaskExecutor(), 10_000);
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
