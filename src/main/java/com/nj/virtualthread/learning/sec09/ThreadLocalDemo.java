package com.nj.virtualthread.learning.sec09;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {
    private static final Logger  logger = LoggerFactory.getLogger(ThreadLocalDemo.class);
    private static final ThreadLocal<String> sessionHolder =  new ThreadLocal<>();

    static void main() throws ExecutionException, InterruptedException {
//        authFilter(ThreadLocalDemo::orderController);
//        authFilter(ThreadLocalDemo::orderController);
//        Thread.ofVirtual().name("request-",1).start(() -> authFilter(ThreadLocalDemo::orderController));
//        Thread.ofVirtual().name("request-",2).start(() -> authFilter(ThreadLocalDemo::orderController));
//        CommonUtils.sleep(Duration.ofSeconds(5));

        try(var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            var request1 = CompletableFuture.supplyAsync(ThreadLocalDemo::callAPI, executorService);
            var request2 = CompletableFuture.supplyAsync(ThreadLocalDemo::callAPI, executorService);

            logger.info("Request 1: {}",  request1.get());
            logger.info("Request 2: {}",  request2.get());
        }
    }

    private static String callAPI(){
        authFilter(ThreadLocalDemo::orderController);
        return sessionHolder.get();
    }

    private static void authFilter(Runnable runnable){
        try {
            var token = authenticate();
            sessionHolder.set(token);
            runnable.run();
        } finally {
            logger.info("Removing Session Holder value: " + sessionHolder.get());
            sessionHolder.remove();
        }
    }

    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        logger.info("Authenticated with token: {}", token);
        return token;
    }

    private static void orderController(){
        logger.info("Order controller : {}", sessionHolder.get() );
        orderService();
    }

    private static void orderService(){
        logger.info("Order service : {}", sessionHolder.get() );
        callProductService();
        callInventoryService();
    }

    // This is a client to call product service
    private static void callProductService(){
        logger.info("calling product-service with header. Authorization: Bearer {}", sessionHolder.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService(){
        logger.info("calling inventory-service with header. Authorization: Bearer {}", sessionHolder.get());
    }
}
