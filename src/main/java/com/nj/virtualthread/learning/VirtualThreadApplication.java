package com.nj.virtualthread.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class VirtualThreadApplication {
    private static final Logger LOG = LoggerFactory.getLogger(VirtualThreadApplication.class);
    static void main(String[] args) {
        var latch = new CountDownLatch(6);
        IntStream.range(0,3).forEach(i -> {
            var thread = Thread.ofVirtual().name("vt-",1).unstarted(() -> {
                IntStream.range(0,3).forEach(j -> {
                    LOG.info("{}", Thread.currentThread());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    latch.countDown();
                    LOG.info("Latch count: {}", latch.getCount());
                });
            });
            thread.start();
            LOG.info("i: {}", i);
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//        thread.join();
    }

}
