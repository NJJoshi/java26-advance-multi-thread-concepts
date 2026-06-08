package com.nj.virtualthread.learning.sec02;

import com.nj.virtualthread.learning.util.CommonUtils;

import java.time.Duration;

public class StackTraceDemo {
    static void main() {
        demo(Thread.ofVirtual().name("virtual-thread-",1));
        CommonUtils.sleep(Duration.ofSeconds(50));
    }

    private static void demo(Thread.Builder threadBuilder) {
        for (int i = 1; i <= 20; i++) {
            int j = i;
            threadBuilder.start(() -> {
                Task.execute(j);
            });
        }
    }
}
