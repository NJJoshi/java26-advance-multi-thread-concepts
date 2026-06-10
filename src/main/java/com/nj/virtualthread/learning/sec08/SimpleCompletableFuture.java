package com.nj.virtualthread.learning.sec08;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {
    private static final Logger  LOG = LoggerFactory.getLogger(SimpleCompletableFuture.class);

    static void main() {
        LOG.info("Starting SimpleCompletableFuture");
//        var ft = fastTask();
        var st = slowTask();
        st.thenAccept(task -> {
           LOG.info("Returned from Task: {}" , task);
        });
        CommonUtils.sleep(Duration.ofSeconds(6));
//        st.join();
        LOG.info("Ending SimpleCompletableFuture");
    }

    private static CompletableFuture<String> fastTask() {
        LOG.info("Starting fast task");
        var task = new CompletableFuture<String>();
        task.complete("Greetings from fast task");
        LOG.info("End fast task");
        return task;
    }

    private static CompletableFuture<String> slowTask() {
        LOG.info("Starting slow task");
        var task = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(5));
            task.complete("Greetings from slow task");
        });
        LOG.info("End slow task");
        return task;
    }
}
