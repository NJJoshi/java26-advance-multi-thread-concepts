package com.nj.virtualthread.learning.sec03;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i) {
        LOG.info("Starting CPU Task. Thread info: {}", Thread.currentThread());
        var timeTaken = CommonUtils.timer(() -> {
            long fib = findFib(i);
            LOG.info("{}th Fibonacci number: {}", i, fib);
        });
        LOG.info("Time taken to execute CPU Task: {} ms", timeTaken);
    }

    // 2 ^ N algorithm - to keep CPU busy, we have intentionally used it.
    private static long findFib(long n) {
        if(n < 2) {
            return n;
        }
        return findFib(n - 1) + findFib(n - 2);
    }


}
