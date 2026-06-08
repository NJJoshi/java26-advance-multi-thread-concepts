package com.nj.virtualthread.learning.sec06;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryDemo {
    private static final Logger logger = LoggerFactory.getLogger(ThreadFactoryDemo.class);
    static void main(String[] args) {
        demo(Thread.ofPlatform().name("NJ-Platform-Thread", 1).factory());
//        demo(Thread.ofVirtual().factory());
        CommonUtils.sleep(Duration.ofSeconds(10));
    }

    private static void demo(ThreadFactory factory){
        for (int i = 0; i < 5; i++) {
            var t = factory.newThread(() -> {
                logger.info("Thread started. {}", Thread.currentThread());
                var ct = factory.newThread(() -> {
                    logger.info("Child thread started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    logger.info("Child thread ended. {}", Thread.currentThread());
                });
                ct.start();
                logger.info("Thread ended. {}", Thread.currentThread());
            });
           t.start();
        }

    }
}
