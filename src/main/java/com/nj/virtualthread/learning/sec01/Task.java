package com.nj.virtualthread.learning.sec01;

import com.nj.virtualthread.learning.VirtualThreadApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(VirtualThreadApplication.class);

    public static void ioIntensive(int i) {
        LOG.info("Start IO Intensive Task {}:{}", i, Thread.currentThread());
        try {
            Thread.sleep(Duration.ofSeconds(10).toMillis());
        } catch (InterruptedException e) {
            LOG.error("IO Intensive Task {} interrupted", i, e);
            Thread.currentThread().interrupt();
        }
        LOG.info("End IO Intensive Task {}:{}",  i, Thread.currentThread());
    }

}
