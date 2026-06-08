package com.nj.virtualthread.learning.sec01;

import com.nj.virtualthread.learning.VirtualThreadApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(VirtualThreadApplication.class);

    public static void ioIntensive(int i) {
        LOG.info("Start IO Intensive Task {}", i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.error("IO Intensive Task {} interrupted", i, e);
            Thread.currentThread().interrupt();
        }
        LOG.info("End IO Intensive Task {}", i);
    }

}
