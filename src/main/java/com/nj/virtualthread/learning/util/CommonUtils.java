package com.nj.virtualthread.learning.util;

import java.time.Duration;

public class CommonUtils {
    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
