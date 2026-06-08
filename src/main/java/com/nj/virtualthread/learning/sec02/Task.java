package com.nj.virtualthread.learning.sec02;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    public static void execute(int i) {
        LOG.info("Start execute Task {}", i);
        try{
            method1(i);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("End execute Task {}",  i);
    }

    private static void method1(int i) {
        LOG.info("Start method1");
        CommonUtils.sleep(Duration.ofSeconds(3));
        try {
            method2(i);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        LOG.info("End method1");
    }

    private static void method2(int i) {
        LOG.info("Start method2");
        CommonUtils.sleep(Duration.ofSeconds(1));
        method3(i);
        LOG.info("End method2");
    }

    private static void method3(int i) {
        LOG.info("Start method3");
        CommonUtils.sleep(Duration.ofSeconds(5));
        if(i == 4){
            throw new IllegalArgumentException("It can't be value i = 4");
        }
        LOG.info("End method3");
    }

}
