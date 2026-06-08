package com.nj.virtualthread.learning.sec05;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SynchronizationDemo1 {
    private static final Logger  logger = LoggerFactory.getLogger(SynchronizationDemo1.class);
    private static final List<Integer> list = Collections.synchronizedList(new ArrayList<>());;

    static void main() {
//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
        logger.info("list size: {}", list.size());
    }

    private static void demo(Thread.Builder threadBuilder) {
        for (int i = 0; i < 50; i++) {
            threadBuilder.start(() -> {
                logger.info("Task Started: {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
            });
        }
    }

    private static void inMemoryTask() {
        list.add(1);
    }

}
