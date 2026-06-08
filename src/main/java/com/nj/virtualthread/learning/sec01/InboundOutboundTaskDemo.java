package com.nj.virtualthread.learning.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_PLATFORM_THREADS = 8;
    private static final int MAX_VIRTUAL_THREADS = 32;

    static void main(String[] args) throws InterruptedException {
//        platformThreadDemo1();
//        try {
//            platformThreadDemo3();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        virtualThreadDemo1();
    }

    /**
     * Create basic traditional way to create thread(s). With Modern JDK, Its called Platform Thread
     */
    private static void platformThreadDemo1() {
       for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
           int j = i;
           Thread thread = new Thread(() -> {
               Task.ioIntensive(j);
           });
           thread.start();
       }
    }

    /**
     *
     * Create Platform thread using Thread.Builder.
     *
     */
    private static void platformThreadDemo2() {
        var builder =
                Thread.ofPlatform().name("NJ-Platform Thread-", 1);
       for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
           int j = i;
           Thread thread =builder.unstarted(() -> {
               Task.ioIntensive(j);
           });
           thread.start();
       }
    }

    /**
     *
     * Create Platform Daemon thread using Thread.Builder and waited for its completion using CountDownLatch class
     *
     * @throws InterruptedException
     */
    private static void platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM_THREADS);

        var builder =
                Thread.ofPlatform().daemon().name("NJ-Daemon Thread-", 1);
       for (int i = 0; i < MAX_PLATFORM_THREADS; i++) {
           int j = i;
           Thread thread =builder.unstarted(() -> {
               Task.ioIntensive(j);
               latch.countDown();
           });
           thread.start();
       }
       latch.await();
    }

    /**
     *
     * Create Virtual thread using Thread.Builder.
     * Virtual threads are Daemon thread by default, so we don't need to specify it explicitly.
     *
     */
    private static void virtualThreadDemo1() throws InterruptedException {
        var countDownLatch = new CountDownLatch(MAX_VIRTUAL_THREADS);
        var builder =
                Thread.ofVirtual().name("NJ-Virtual Thread-", 1);
        for (int i = 0; i < MAX_VIRTUAL_THREADS; i++) {
            int j = i;
            Thread thread =builder.unstarted(() -> {
                Task.ioIntensive(j);
                countDownLatch.countDown();
            });
            thread.start();
        }
        countDownLatch.await();
    }
}
