package com.nj.virtualthread.learning.sec03;

import com.nj.virtualthread.learning.util.CommonUtils;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class CPUTaskDemo {
    private static final Logger LOG = Logger.getLogger(CPUTaskDemo.class.getName());
    private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors() * 3;

    public static void main() {
        demo(Thread.ofVirtual());
    }

    private static void demo(Thread.Builder threadBuilder) {
        var latch = new CountDownLatch(TASK_COUNT);
        for (int i = 0; i < TASK_COUNT; i++) {
            threadBuilder.start(() -> {
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
