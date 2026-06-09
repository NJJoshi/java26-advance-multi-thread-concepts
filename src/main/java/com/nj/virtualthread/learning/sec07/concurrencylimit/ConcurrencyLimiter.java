package com.nj.virtualthread.learning.sec07.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrencyLimiter implements AutoCloseable {

    private static final Logger  logger = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executor;
    private final Semaphore semaphore;
    private Queue<Callable<?>> queue;

    public ConcurrencyLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        this.queue.add(callable);
        return executor.submit(() -> executeTask());
    }

    private <T> T executeTask() {
        try{
            semaphore.acquire();
            return (T) this.queue.poll().call();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executor.close();
    }
}
