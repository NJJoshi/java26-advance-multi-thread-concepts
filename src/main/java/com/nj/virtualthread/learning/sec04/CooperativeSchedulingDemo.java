package com.nj.virtualthread.learning.sec04;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CooperativeSchedulingDemo {
    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class.getName());

    static {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
    }

    static void main() {
        var threadBuilder = Thread.ofVirtual();
        var t1 = threadBuilder.unstarted(() -> demo(1));
        var t2 = threadBuilder.unstarted(() -> demo(2));
        t1.start();
        t2.start();
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(int threadNumber) {
        log.info("Starting " + threadNumber + " thread");
        for (int i = 0; i < 10; i++) {
            log.info(" Printing {} by thread-{}. Thread :{}",i, threadNumber, Thread.currentThread());
        }
        log.info("Done " + threadNumber + " thread");
    }
}
